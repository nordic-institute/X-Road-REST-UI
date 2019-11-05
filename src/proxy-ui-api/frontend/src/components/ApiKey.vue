<template>
  <div class="wrapper">
    APIKEY
    <div v-for="item in form.inputs" v-bind:key="item.id" class="row-wrap">
      <div class="label">
        {{item.label}}
        <helpIcon v-if="item.help" :text="item.help" />
      </div>

      <div class="input-wrap">
        <v-text-field
          v-if="item.type === 'textField'"
          id="username"
          name="username"
          type="text"
          v-model="item.value"
          :disabled="item.disabled"
        ></v-text-field>

        <v-select
          v-if="item.type === 'select'"
          :items="item.items"
          class="select-connection"
          v-model="item.value"
          :disabled="item.disabled"
        ></v-select>
      </div>
    </div>

    <large-button class="save-button" @click="save()">{{$t('action.save')}}</large-button>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import HelpIcon from '@/components/HelpIcon.vue';
import LargeButton from '@/components/LargeButton.vue';

export default Vue.extend({
  components: {
    HelpIcon,
    LargeButton,
  },
  props: {},
  data() {
    return {
      form: {
        inputs: [
          {
            type: 'select',
            label: 'my select',
            help: 'yada yada',
            id: '2378',
            value: 'Bar',
            items: ['Foo', 'Bar', 'Fizz', 'Buzz'],
          },
          {
            type: 'textField',
            label: 'my text field',
            help: 'bla lbaa',
            id: '32434',
          },
          {
            type: 'textField',
            label: 'my text field',
            id: '99434',
          },
          {
            type: 'textField',
            label: 'test disabled',
            help: 'oh noes',
            id: '76594',
            disabled: true,
            value: 'foo',
          },
        ],
      },
    };
  },
  methods: {
    save(): void {
      console.log(this.form.inputs);
    },
  },
});
</script>

<style lang="scss" scoped>
.row-wrap {
  display: flex;
  flex-direction: row;
  align-items: baseline;
}
.label {
  width: 130px;
  display: flex;
  flex-direction: row;
  align-items: baseline;
}

.input-wrap {
  width: 300px;
}
</style>