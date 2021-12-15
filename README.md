## AWS S3 사용하여 Spring Boot 파일업로드 API 구현하기

- Spring Boot로 구축한 API서버로 들어오는 파일 업로드 요청을 바탕으로 파일을 S3 저장한다.

### 1. Gradle Import

```java
  dependencies {
    implementation 'org.springframework.cloud:spring-cloud-starter-aws:2.0.1.RELEASE'
  }
```


### 2. aws.yml 작성
```
cloud:
  aws:
    credentials:
      accessKey: ACCESS_KEY
      secretKey: SECRET_KEY

    s3:
      bucket: BUCKET_NAME
    region:
      static: ap-northeast-2
    stack:
      auto: false

```


### 3. AmazonS3 Bean 생성

```java
@Configuration
public class AmazonS3Config {

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public AmazonS3 amazonS3() {
        // iam 계정 기반 credential 생성
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        System.out.println("awsCreds = " + awsCreds);
        return  AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }
}

```

### 4. 사용
```java
    private final AmazonS3 amazonS3;

    amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(
                CannedAccessControlList.PublicRead));
```


---
Check my code for <a href="https://github.com/Youhoseong/aws-s3-spring/blob/main/src/main/java/com/aws/lambda/spring/service/S3UploaderService.java">detail</a>.
