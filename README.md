## Preface
"Shopnest" The project is committed to building a complete e-commerce system, using the mainstream technology at this stage
## Introduction of the project
The project is an e-commerce system, including the front-end store system and the back-end management system, based on SpringBoot, using Docker containerized deployment. The front desk mall system includes home page portal, product recommendation, product search, product display, shopping cart, order flow, member center, customer service, help center and other modules. The back-end management system includes commodity management, order management, member management, promotion management, operation management, content management, statistical reports, financial management, rights management, settings and other modules.
## Organizational structure
mall
├── mall-common -- tools and common code
├── mall-core -- JPA-based database operations (replaces mall-mbg)
├── mall-security -- SpringSecurity encapsulation common module
├── mall-admin -- backend mall management system interface
├── mall-search -- commodity search system based on Elasticsearch
└──mall-portal -- frontend mall system interface
## Technical selection
| Technology            | Description                       | Official Site                                |
|-----------------------|-----------------------------------|----------------------------------------------|
| Spring Boot           | Web Application Framework         | https://spring.io/projects/spring-boot       |
| Spring Security       | Authentication & Authorization     | https://spring.io/projects/spring-security   |
| Spring Data JPA       | ORM for Database Operations       | https://spring.io/projects/spring-data-jpa  |
| Elasticsearch         | Search Engine                     | https://github.com/elastic/elasticsearch     |
| RabbitMQ              | Message Queue                     | https://www.rabbitmq.com                    |
| Redis                 | In-Memory Data Store              | https://redis.io                            |
| MongoDB               | NoSQL Database                    | https://www.mongodb.com                     |
| LogStash              | Log Collection Tool               | https://github.com/elastic/logstash         |
| Kibana                | Log Visualization Tool            | https://github.com/elastic/kibana           |
| Nginx                 | Static Resource Server            | https://www.nginx.com                       |
| Docker                | Application Container Engine      | https://www.docker.com                      |
| Jenkins               | Automation Deployment Tool        | https://github.com/jenkinsci/jenkins        |
| AWS S3                | Object Storage                    | https://aws.amazon.com/s3/                  |
| MinIO                 | Object Storage (Alternative)      | https://github.com/minio/minio              |
| JWT                   | JWT Login Support                 | https://github.com/jwtk/jjwt                |
| Lombok                | Java Language Enhancement         | https://projectlombok.org/      |
| SpringDoc             | API Documentation Tool            | https://github.com/springdoc/springdoc-openapi |
| Hibernate Validator   | Validation Framework              | http://hibernate.org/validator              |
