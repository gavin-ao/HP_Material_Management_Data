package data.driven.cto.util;

import org.bson.types.ObjectId;

import java.net.URLEncoder;

/**
 * @author 何晋凯
 * @date 2018/06/04
 */
public class UUIDUtil {

    public static String getUUID(){
        return new ObjectId().toString();
    }

    public static void main(String[] args) throws Exception{
        String str =URLEncoder.encode("{\"addrId\":\"5bb2817bc2d07f16587402de\",\"realPayment\":1200,\"detailList\":[{\"commodityId\":\"1\",\"amount\":2}]}", "UTF-8");
        System.out.println(str);
//        String sql = "INSERT INTO `reward_act_command` (`command_id`, `command`, `command_type`, `act_id`, `user_id`, `app_info_id`, `used`, `create_at`) VALUES ";
//        StringBuilder sb = new StringBuilder(sql);
//        for (int i = 0; i < 3000; i++){
//            String b = "('" + getUUID() + "', '￥dA7EbeKlVlO￥', '2', '7', 'fkxgzx', '5b699c9171c8a90ec8201702', '0', '2018-09-19 21:06:00')";
//            sb.append(b).append(",");
//        }
//        System.out.println(sb.delete(sb.length()-1,sb.length()));

//        System.out.println(getUUID());
    }
}
