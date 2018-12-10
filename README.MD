# 工具类

## 一、 该工程包含的工具类及其说明

|工具类名|入参|描述|额外|
|:---:|:---:|:---:|:---:|
|ExcelUtils|待读取的excel文件，是否读取首行，需要读取的列（序列从0开始），工作表序列（从0开始）|暂时只支持读excel，没有写excel|还可生成包含表头key的json串|
|SSHUtils|服务器ip，用户名，密码|执行远程服务器命令|也可执行当前服务器命令|
|DBFUtils|读取：dbf文件；写入：dbf文件，Map<String, String[]> key有三个，put使用时分别为columnNames, columnDataType(数据类型的全字符小写形式), columnDataLength；写入的记录数组|支持读写DBF文件|写DBF文件暂时只支持string类型|
|IpUtils|-|获取当前机器的IP地址|目前只支持windows和linux操作系统|