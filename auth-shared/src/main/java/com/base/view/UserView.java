package com.base.view;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "users")
public class UserView {
    @Id
    @Field(type = FieldType.Keyword)
    private String id;
    @Field(type = FieldType.Text)
    private String fullName;
    @Field(type = FieldType.Keyword)
    private String fullNameSort;
    @Field(type = FieldType.Date)
    private Date dateOfBirth;
    @Field(type = FieldType.Keyword)
    private String phoneNumber;
    @Field(type = FieldType.Keyword)
    private String email;
    @Field(type = FieldType.Keyword)
    private String username;
    @Field(type = FieldType.Keyword)
    private Integer status;
    @Field(type = FieldType.Date)
    private Date createdDate;
}
