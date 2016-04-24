namespace java me.gitai.phuckqq.data

typedef i32 int
typedef string String
typedef i64 long
typedef bool boolean

struct QQMessage {
  1:long _id,
  2:int _status,

  3:int extInt = -1,
  4:int extLong = -1,
  5:String extStr,
  6:int extraflag,
  7:String frienduin,
  8:boolean isValid,
  9:boolean isread,
  10:int issend,
  11:int istroop,
  12:int longMsgCount,
  13:int longMsgId,
  14:int longMsgIndex,
  15:String msg,
  16:binary msgData,
  17:long msgId,
  18:long msgUid,
  19:long msgseq,
  20:int msgtype,
  21:String selfuin,
  22:int sendFailCode,
  23:String senderuin,
  24:long shmsgseq,
  25:long time,
  26:long uniseq,
  27:int versionCode,
  28:long vipBubbleID,

  29:String actMsgContentValue,
  30:String action,
  31:int bizType,
  32:int counter,
  33:String emoRecentMsg,
  34:long fileSize,
  35:int fileType,
  36:boolean hasReply,
  37:boolean isCacheValid,
  38:boolean isInWhisper,
  39:String latestNormalMsgString,
  40:String nickName,
  41:String pttUrl,
  42:long shareAppID,
  43:int unReadNum,
  44:String summary
}

service QQService{
	boolean sendMsg(1:String frienduin, 2:String msg);
	String getNickName(1:String selfuin, 2:String senderuin, 3:String frienduin);
}