/*
 Navicat Premium Data Transfer

 Source Server         : 47.97.118.22
 Source Server Type    : MongoDB
 Source Server Version : 40203
 Source Host           : 47.97.118.22:27017
 Source Schema         : dev1

 Target Server Type    : MongoDB
 Target Server Version : 40203
 File Encoding         : 65001

 Date: 13/03/2020 18:02:53
*/


// ----------------------------
// Collection structure for user
// ----------------------------
db.getCollection("user").drop();
db.createCollection("user");

// ----------------------------
// Documents of user
// ----------------------------
db.getCollection("user").insert([ {
    _id: NumberLong("1"),
    userName: "a",
    age: NumberInt("11"),
    creatTime: ISODate("2020-02-16T08:53:18.035Z"),
    sex: "女"
} ]);
db.getCollection("user").insert([ {
    _id: NumberLong("2"),
    userName: "b",
    age: NumberInt("12"),
    creatTime: ISODate("2020-02-16T08:53:39.633Z"),
    sex: "女"
} ]);
db.getCollection("user").insert([ {
    _id: NumberLong("6"),
    userName: "aaa",
    age: NumberInt("13"),
    creatTime:  ISODate("2020-03-16T10:33:29.977Z"),
    sex: "女"
} ]);
db.getCollection("user").insert([ {
    _id: 3,
    age: 14,
    creatTime: ISODate("2020-03-17 08:53:18.035"),
    userName: 111,
    sex: "女"
} ]);
db.getCollection("user").insert([ {
    _id: NumberLong("13"),
    userName: "lei",
    age: NumberInt("13"),
    creatTime: "2019-03-17 08:53:18.035",
    sex: "女"
} ]);
db.getCollection("user").insert([ {
    _id: NumberLong("15"),
    userName: "dqwqeas",
    sex: "男",
    age: NumberInt("12"),
    creatTime: ISODate("2020-03-16T10:33:29.977Z")
} ]);
db.getCollection("user").insert([ {
    _id: NumberLong("16"),
    userName: "lei2",
    age: NumberInt("13"),
    creatTime: ISODate("2020-02-16T10:35:35.752Z"),
    sex: "女"
} ]);
db.getCollection("user").insert([ {
    _id: NumberLong("17"),
    userName: "dfgd",
    sex: "男",
    age: NumberInt("13"),
    creatTime: ISODate("2020-02-16T10:36:46.198Z")
} ]);
db.getCollection("user").insert([ {
    _id: NumberLong("18"),
    userName: "lei",
    sex: "男",
    age: NumberInt("13"),
    creatTime: ISODate("2020-02-16T10:38:25.189Z")
} ]);
