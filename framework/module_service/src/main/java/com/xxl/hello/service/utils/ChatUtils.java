package com.xxl.hello.service.utils;

import com.xxl.hello.service.data.model.entity.im.MessageDirection;
import com.xxl.hello.service.data.model.entity.im.MessageEntity;
import com.xxl.hello.service.data.model.entity.im.SDKMessage;
import com.xxl.hello.service.data.model.enums.ChatEnumsApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author xxl.
 * @date 2026/5/15.
 */
public class ChatUtils {

    /**
     * 生成随机聊天消息（默认20-30条）
     */
    public static List<MessageEntity> generateRandomMessages() {
        return generateRandomMessages(20, 30);
    }

    /**
     * 生成随机聊天消息（指定范围数量）
     *
     * @param minCount 最小消息数量
     * @param maxCount 最大消息数量
     * @return 随机生成的消息列表
     */
    public static List<MessageEntity> generateRandomMessages(int minCount, int maxCount) {
        // 参数校验
        if (minCount < 0 || maxCount < minCount) {
            throw new IllegalArgumentException("Invalid message count range: min=" + minCount + ", max=" + maxCount);
        }

        List<MessageEntity> messages = new ArrayList<>();
        int count = new Random().nextInt(maxCount - minCount + 1) + minCount;
        for (int i = 0; i < count; i++) {
            MessageEntity message = createRandomMessage(i);
            messages.add(message);
        }
        return messages;
    }

    /**
     * 创建随机消息
     *
     * @param index 消息索引
     * @return 随机生成的消息
     */
    public static MessageEntity createRandomMessage(int index) {
        SDKMessage sdkMessage = SDKMessage.obtain()
                .setSessionType(0)
                .setMessageTime(System.currentTimeMillis() - (30 - index) * 60000)
                .setMessageStatus(1);

        MessageEntity message = MessageEntity.obtain(sdkMessage);

        boolean isRight = new Random().nextBoolean();
        message.setMessageDirection(isRight ? MessageDirection.RIGHT : MessageDirection.LEFT);
        message.setMessageType(ChatEnumsApi.MessageType.TEXT);
        sdkMessage.setTextContent(getRandomText());
        return message;
    }

    /**
     * 获取随机文本内容
     *
     * @return 随机文本
     */
    private static String getRandomText() {
        String[] texts = {
                // 日常问候
                "你好啊！",
                "嗨，在吗？",
                "早上好呀",
                "晚上好~",
                "周末愉快！",

                // 天气相关
                "今天天气真好",
                "外面下雨了，记得带伞",
                "今天有点冷啊",
                "天气不错，适合出门",
                "雾霾好大，注意防护",

                // 近况关心
                "最近忙什么呢？",
                "好久不见，最近怎么样",
                "最近过得还好吗",
                "工作还顺利吧",
                "身体怎么样，多注意休息",

                // 邀约
                "周末一起出去玩吧",
                "晚上一起吃饭？",
                "要不要一起喝杯咖啡",
                "改天约个饭呗",
                "有空出来聚聚",

                // 肯定回复
                "好的，没问题",
                "收到，谢谢！",
                "好的，我知道了",
                "没问题，交给我",
                "OK，就这么定了",
                "收到收到",
                "好的，明白了",
                "没问题，随时联系",
                "可以，没问题",
                "行，听你的",

                // 赞许认可
                "这个想法不错",
                "我觉得可以",
                "太棒了！",
                "厉害了！",
                "你说得对",
                "有道理诶",
                "真不错！",
                "靠谱！",

                // 约定时间
                "明天见",
                "到时候见",
                "不见不散",
                "晚点聊",

                // 开心幽默
                "哈哈，是的",
                "我也这么觉得",
                "那我们约个时间",
                "笑死，哈哈哈",
                "绝了！",
                "说得太对了",

                // 工作协作
                "这个需求我看看",
                "稍等，马上处理",
                "文件收到了，谢谢",
                "会议几点开始？",
                "文档我更新好了",

                // 其他日常
                "随便，都行",
                "让我想想",
                "等一下哈",
                "真的假的？",
                "原来如此",
                "明白了明白了",
                "别客气",
                "没事没事"
        };
        return texts[new Random().nextInt(texts.length)];
    }
}