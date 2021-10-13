@Conditional派生注解	作用(都是判断是否符合指定的条件)

@ConditionalOnJava	系统的java版本是否符合要求

@ConditionalOnBean	有指定的Bean类

@ConditionalOnMissingBean	没有指定的bean类

@ConditionalOnExpression	符合指定的SpEL表达式

@ConditionalOnClass	有指定的类

@ConditionalOnMissingClass	没有指定的类

@ConditionalOnSingleCandidate	容器只有一个指定的bean，或者这个bean是首选bean

@ConditionalOnProperty	指定的property属性有指定的值

@ConditionalOnResource	路径下存在指定的资源

@ConditionalOnWebApplication	系统环境是web环境

@ConditionalOnNotWebApplication	系统环境不是web环境

@ConditionalOnjndi	JNDI存在指定的项