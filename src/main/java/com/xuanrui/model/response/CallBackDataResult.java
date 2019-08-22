package com.xuanrui.model.response;

import java.util.List;

/**
 * @Description: 回调请求包
 * @Author: gdc
 * @Date: 2019-08-21 15:39
 **/
public class CallBackDataResult {
    private String From_Account;
    private String To_Account;
    private Long MsgSeq;
    private Long MsgRandom;
    private Long MsgTime;
    private List<MsgBody> MsgBody;

    public String getFrom_Account() {
        return From_Account;
    }

    public void setFrom_Account(String from_Account) {
        From_Account = from_Account;
    }

    public String getTo_Account() {
        return To_Account;
    }

    public void setTo_Account(String to_Account) {
        To_Account = to_Account;
    }

    public Long getMsgSeq() {
        return MsgSeq;
    }

    public void setMsgSeq(Long msgSeq) {
        MsgSeq = msgSeq;
    }

    public Long getMsgRandom() {
        return MsgRandom;
    }

    public void setMsgRandom(Long msgRandom) {
        MsgRandom = msgRandom;
    }

    public Long getMsgTime() {
        return MsgTime;
    }

    public void setMsgTime(Long msgTime) {
        MsgTime = msgTime;
    }

    public List<CallBackDataResult.MsgBody> getMsgBody() {
        return MsgBody;
    }

    public void setMsgBody(List<CallBackDataResult.MsgBody> msgBody) {
        MsgBody = msgBody;
    }

    public static class MsgBody {
        private String MsgType;

        private MsgContent MsgContent;


        public String getMsgType() {
            return MsgType;
        }

        public void setMsgType(String msgType) {
            MsgType = msgType;
        }

        public CallBackDataResult.MsgContent getMsgContent() {
            return MsgContent;
        }

        public void setMsgContent(CallBackDataResult.MsgContent msgContent) {
            MsgContent = msgContent;
        }
    }

    public static class MsgContent {
        //文本消息
        private String Text;

        //地理位置消息
        //地理位置描述信息
        private String Desc;
        // 纬度
        private Double Latitude;
        //经度
        private Double Longitude;

        //表情消息
        private Integer Index;
        private String Data;

        //语音消息
        //语音序列号 后台用于索引语音的键值
        private String UUID;
        //语音数据大小单位：字节
        private Double Size;
        //语音时长单位：秒
        private Double Second;
        //资源下载地址
        private String Url;

        //图像消息
        //图片序列号。后台用于索引图片的键值。
        //private String UUID;
        //图片格式。JPG = 1，GIF = 2，PNG = 3，BMP = 4，其他 = 255。
        private Integer ImageFormat;
        private List<ImageInfoArray> ImageInfoArray;


        //文件消息
        //图片序列号。后台用于索引语音的键值。
        //private String UUID;
        private Double FileSize;
        private String FileName;


        //自定义消息
        //自定义消息数据。 不作为 APNs 的 payload 字段下发，故从 payload 中无法获取 Data 字段。
        //private String Data;
        //	自定义消息描述信息；当接收方为 iOS 或 Android 后台在线时，做离线推送文本展示。
        //private String Desc;
        //扩展字段；当接收方为 iOS 系统且应用处在后台时，此字段作为 APNs 请求包 Payloads 中的 Ext 键值下发，Ext 的协议格式由业务方确定，APNs 只做透传。
        private String Ext;
        //自定义 APNs 推送铃音。
        private String Sound;

        public void setUrl(String url) {
            Url = url;
        }

        public String getUrl() {
            return Url;
        }
        

        public String getText() {
            return Text;
        }

        public void setText(String text) {
            Text = text;
        }

        public String getDesc() {
            return Desc;
        }

        public void setDesc(String desc) {
            Desc = desc;
        }

        public Double getLatitude() {
            return Latitude;
        }

        public void setLatitude(Double latitude) {
            Latitude = latitude;
        }

        public Double getLongitude() {
            return Longitude;
        }

        public void setLongitude(Double longitude) {
            Longitude = longitude;
        }

        public Integer getIndex() {
            return Index;
        }

        public void setIndex(Integer index) {
            Index = index;
        }

        public String getData() {
            return Data;
        }

        public void setData(String data) {
            Data = data;
        }

        public String getUUID() {
            return UUID;
        }

        public void setUUID(String UUID) {
            this.UUID = UUID;
        }

        public Double getSize() {
            return Size;
        }

        public void setSize(Double size) {
            Size = size;
        }

        public Double getSecond() {
            return Second;
        }

        public void setSecond(Double second) {
            Second = second;
        }

        public void setFileSize(Double fileSize) {
            FileSize = fileSize;
        }

        public Integer getImageFormat() {
            return ImageFormat;
        }

        public void setImageFormat(Integer imageFormat) {
            ImageFormat = imageFormat;
        }

        public List<MsgContent.ImageInfoArray> getImageInfoArray() {
            return ImageInfoArray;
        }

        public void setImageInfoArray(List<MsgContent.ImageInfoArray> imageInfoArray) {
            ImageInfoArray = imageInfoArray;
        }

        public Double getFileSize() {
            return FileSize;
        }

        public String getFileName() {
            return FileName;
        }

        public void setFileName(String fileName) {
            FileName = fileName;
        }

        public String getExt() {
            return Ext;
        }

        public void setExt(String ext) {
            Ext = ext;
        }

        public String getSound() {
            return Sound;
        }

        public void setSound(String sound) {
            Sound = sound;
        }

        public static class ImageInfoArray{
            //图片类型： 1-原图，2-大图，3-缩略图。
            private Integer Type;
            //图片数据大小，单位：字节。
            private Double Size;
            //图片宽度。
            private Double Width;
            //图片高度。
            private Double Height;
            //图片下载地址。
            private String URL;

            public Integer getType() {
                return Type;
            }

            public void setType(Integer type) {
                Type = type;
            }

            public Double getSize() {
                return Size;
            }

            public void setSize(Double size) {
                Size = size;
            }

            public Double getWidth() {
                return Width;
            }

            public void setWidth(Double width) {
                Width = width;
            }

            public Double getHeight() {
                return Height;
            }

            public void setHeight(Double height) {
                Height = height;
            }

            public String getURL() {
                return URL;
            }

            public void setURL(String URL) {
                this.URL = URL;
            }
        }

        //Data	String	自定义消息数据。 不作为 APNs 的 payload 字段下发，故从 payload 中无法获取 Data 字段。
        //Desc	String	自定义消息描述信息；当接收方为 iOS 或 Android 后台在线时，做离线推送文本展示。
        //Ext	String	扩展字段；当接收方为 iOS 系统且应用处在后台时，此字段作为 APNs 请求包 Payloads 中的 Ext 键值下发，Ext 的协议格式由业务方确定，APNs 只做透传。
        //Sound	String	自定义 APNs 推送铃音。

    }


}
    