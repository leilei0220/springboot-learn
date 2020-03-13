/*
 Navicat Premium Data Transfer

 Source Server         : 47.97.118.22 dev2
 Source Server Type    : MongoDB
 Source Server Version : 40203
 Source Host           : 47.97.118.22:27017
 Source Schema         : dev2

 Target Server Type    : MongoDB
 Target Server Version : 40203
 File Encoding         : 65001

 Date: 13/03/2020 18:06:37
*/


// ----------------------------
// Collection structure for role
// ----------------------------
db.getCollection("role").drop();
db.createCollection("role");

// ----------------------------
// Documents of role
// ----------------------------
db.getCollection("role").insert([ {
    _id: NumberLong("1"),
    age: NumberInt("19"),
    creatTime: ISODate("2020-02-16T08:26:36.019Z"),
    _class: "com.example.demo.entity.two.Role"
} ]);
