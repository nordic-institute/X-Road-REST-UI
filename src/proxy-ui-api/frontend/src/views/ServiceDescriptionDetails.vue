<template>
  <div class="xrd-tab-max-width">
    <div>
      <subViewTitle
        v-if="serviceDesc && serviceDesc.type === 'WSDL'"
        :title="$t('services.wsdlDetails')"
        @close="close"
      />
      <subViewTitle
        v-if="serviceDesc && serviceDesc.type === 'REST'"
        :title="$t('services.restDetails')"
        @close="close"
      />
      <div class="delete-wrap">
        <large-button
          v-if="showDelete"
          @click="confirmDelete = true"
          outlined
        >{{$t('action.delete')}}</large-button>
      </div>
    </div>

    <ValidationObserver ref="form" v-slot="{ validate, invalid }">
      <div class="edit-row">
        <div>{{$t('services.editUrl')}}</div>

        <ValidationProvider
          v-if="serviceDesc && serviceDesc.type === 'WSDL'"
          rules="required|wsdlUrl"
          name="url"
          v-slot="{ errors }"
          class="validation-provider"
        >
          <v-text-field
            v-model="serviceDesc.url"
            single-line
            class="url-input"
            name="url"
            :error-messages="errors"
            type="text"
            @input="touched = true"
          ></v-text-field>
        </ValidationProvider>
      </div>

      <div class="edit-row">
        <template v-if="serviceDesc && serviceDesc.type === 'REST'">
          <div>{{$t('services.editServiceCode')}}</div>

          <ValidationProvider
            rules="required"
            name="code_field"
            v-slot="{ errors }"
            class="validation-provider"
          >
            <v-text-field
              v-model="serviceDesc.code"
              single-line
              class="code-input"
              name="code_field"
              type="text"
              :maxlength="255"
              :error-messages="errors"
              @input="touched = true"
            ></v-text-field>
          </ValidationProvider>
        </template>
      </div>

      <v-card flat>
        <div class="footer-button-wrap">
          <large-button @click="close()" outlined>{{$t('action.cancel')}}</large-button>
          <large-button
            class="save-button"
            :loading="saveBusy"
            @click="save()"
            :disabled="!touched || invalid"
          >{{$t('action.save')}}</large-button>
        </div>
      </v-card>
    </ValidationObserver>

    <!-- Confirm dialog delete WSDL -->
    <confirmDialog
      :dialog="confirmDelete"
      title="services.deleteTitle"
      text="services.deleteWsdlText"
      @cancel="confirmDelete = false"
      @accept="doDeleteServiceDesc()"
    />
    <!-- Confirm dialog for warnings when editing WSDL -->
    <warningDialog
      :dialog="confirmEditWarning"
      :warnings="warningInfo"
      @cancel="cancelEditWarning()"
      @accept="acceptEditWarning()"
    ></warningDialog>
  </div>
</template>

<script lang="ts">
/***
 * Component for showing the details of REST or WSDL service description.
 * Both use the same api.
 */
import Vue from 'vue';
import _ from 'lodash';
import axios from 'axios';
import { ValidationProvider, ValidationObserver } from 'vee-validate';
import { mapGetters } from 'vuex';
import { Permissions } from '@/global';
import SubViewTitle from '@/components/SubViewTitle.vue';
import ConfirmDialog from '@/components/ConfirmDialog.vue';
import WarningDialog from '@/components/WarningDialog.vue';
import LargeButton from '@/components/LargeButton.vue';

export default Vue.extend({
  components: {
    SubViewTitle,
    ConfirmDialog,
    WarningDialog,
    LargeButton,
    ValidationProvider,
    ValidationObserver,
  },
  props: {
    id: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      confirmDelete: false,
      confirmEditWarning: false,
      warningInfo: [],
      touched: false,
      serviceDesc: undefined,
      saveBusy: false,
    };
  },
  computed: {
    showDelete(): boolean {
      return this.$store.getters.hasPermission(Permissions.DELETE_WSDL);
    },
  },
  methods: {
    close(): void {
      this.$router.go(-1);
    },

    save(): void {
      this.saveBusy = true;
      axios
        .patch(`/service-descriptions/${this.id}`, this.serviceDesc)
        .then((res) => {
          this.$bus.$emit('show-success', 'localGroup.descSaved');
          this.saveBusy = false;
          this.$router.go(-1);
        })
        .catch((error) => {
          if (error.response.data.warnings) {
            this.warningInfo = error.response.data.warnings;
            this.confirmEditWarning = true;
          } else {
            this.$bus.$emit('show-error', error.message);
            this.saveBusy = false;
          }
        });
    },

    fetchData(id: string): void {
      axios
        .get(`/service-descriptions/${id}`)
        .then((res) => {
          this.serviceDesc = res.data;
        })
        .catch((error) => {
          this.$bus.$emit('show-error', error.message);
        });
    },
    doDeleteServiceDesc(): void {
      this.confirmDelete = false;

      axios
        .delete(`/service-descriptions/${this.id}`)
        .then(() => {
          this.$bus.$emit('show-success', 'services.deleted');
          this.$router.go(-1);
        })
        .catch((error) => {
          this.$bus.$emit('show-error', error.message);
        });
    },

    acceptEditWarning(): void {
      const tempDesc: any = this.serviceDesc;

      if (!tempDesc) {
        return;
      }

      tempDesc.ignore_warnings = true;

      axios
        .patch(`/service-descriptions/${this.id}`, tempDesc)
        .then((res) => {
          this.$bus.$emit('show-success', 'localGroup.descSaved');
          this.$router.go(-1);
        })
        .catch((error) => {
          this.$bus.$emit('show-error', error.message);
        })
        .finally(() => {
          this.saveBusy = false;
        });
    },

    cancelEditWarning(): void {
      this.confirmEditWarning = false;
      this.saveBusy = false;
    },
  },
  created() {
    this.fetchData(this.id);
  },
});
</script>

<style lang="scss" scoped>
@import '../assets/colors';
@import '../assets/dialogs';

.edit-row {
  display: flex;
  align-content: center;
  align-items: baseline;
  margin-top: 30px;

  > div {
    min-width: 90px;
  }

  .code-input {
    margin-left: 60px;
  }
  .url-input {
    margin-left: 60px;
  }
}

.delete-wrap {
  margin-top: 50px;
  display: flex;
  justify-content: flex-end;
}

.footer-button-wrap {
  margin-top: 48px;
  display: flex;
  justify-content: flex-end;
  border-top: 1px solid $XRoad-Grey40;
  padding-top: 20px;
}

.save-button {
  margin-left: 20px;
}
</style>

