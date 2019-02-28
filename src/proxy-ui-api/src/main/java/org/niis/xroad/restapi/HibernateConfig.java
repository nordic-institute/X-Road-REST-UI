package org.niis.xroad.restapi;

//@Configuration
//@EnableTransactionManagement
public class HibernateConfig {

    // TO DO :
//    configuration.setInterceptor(interceptor);
//    applyDatabasePropertyFile(configuration, name);
//    applySystemProperties(configuration, name);
// multiple facotries: https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-two-datasources
    // config locations: https://stackoverflow.com/a/10820111/1469083
    // basic "configure custom datasource":
    // https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-configure-a-datasource


//    @Bean
//    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setConfigLocations(new ClassPathResource("hibernate.cfg.xml"),
//                new ClassPathResource("serverconf.hibernate.cfg.xml"));
//        sessionFactory.setDataSource(dataSource);
//        return sessionFactory;
//    }
//
//    @Bean
//    public DataSource dataSource() {
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
//        dataSource.setJdbcUrl("dbc:hsqldb:mem:.");
//        dataSource.setUsername("sa");
//        dataSource.setPassword("");
//        return dataSource;
//    }

//    @Bean
//    public DataSource dataSource() {
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
//        dataSource.setUrl("dbc:hsqldb:mem:.");
//        dataSource.setUsername("sa");
//        dataSource.setPassword("");
//        return dataSource;
//    }

}
