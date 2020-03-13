/*
 Navicat Premium Data Transfer

 Source Server         : 47.97.118.22 dev3
 Source Server Type    : MongoDB
 Source Server Version : 40203
 Source Host           : 47.97.118.22:27017
 Source Schema         : dev3

 Target Server Type    : MongoDB
 Target Server Version : 40203
 File Encoding         : 65001

 Date: 13/03/2020 18:07:18
*/


// ----------------------------
// Collection structure for student
// ----------------------------
db.getCollection("student").drop();
db.createCollection("student");

// ----------------------------
// Documents of student
// ----------------------------
db.getCollection("student").insert([ {
    _id: NumberLong("1"),
    studentName: "aaa",
    age: NumberInt("13"),
    creatTime: ISODate("2020-02-16T08:47:15.954Z"),
    _class: "com.example.demo.entity.three.Student"
} ]);
db.getCollection("student").insert([ {
    _id: NumberLong("6"),
    studentName: "aaa",
    age: NumberInt("13"),
    creatTime: ISODate("2020-02-16T08:57:47.107Z")
} ]);
db.getCollection("student").insert([ {
    _id: NumberLong("13"),
    studentName: "lei",
    age: NumberInt("13"),
    creatTime: ISODate("2020-02-16T10:32:43.62Z")
} ]);
db.getCollection("student").insert([ {
    _id: NumberLong("16"),
    studentName: "lei2",
    age: NumberInt("13"),
    creatTime: ISODate("2020-02-16T10:35:35.752Z")
} ]);
db.getCollection("student").insert([ {
    _id: NumberLong("17"),
    studentName: "dfgd",
    age: NumberInt("13"),
    creatTime: ISODate("2020-02-16T10:36:46.198Z")
} ]);
db.getCollection("student").insert([ {
    _id: NumberLong("18"),
    studentName: "lei",
    age: NumberInt("13"),
    creatTime: ISODate("2020-02-16T10:38:25.189Z")
} ]);
