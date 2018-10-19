package data.driven.cto.util;

import com.alibaba.fastjson.JSON;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 初始化数据，导入操作
 * @author hejinkai
 * @date 2018/9/28
 */
public class ImportExcelUtil {
    private static final String filePath = "E:/work/newwork/文档/hpjsq/数据/20180928/hp340示例数据.xlsx";
    private static final String picturePath = "E:/work/newwork/文档/hpjsq/数据/20180928/340笔记本图片/";

    public static void main(String[] args)throws Exception{
        String key = "显卡,系统,内存,硬盘,液晶屏,光驱,指纹识别,电池,无线网卡,保修";
        List<String> keyList = Arrays.asList(key.split(","));

        String preKey = "标准配置,最受欢迎,日常办公";
        List<String> preKeyList = Arrays.asList(preKey.split(","));

        String path = "E:/pj.txt";
        List<String> list = Files.readAllLines(Paths.get(path));
        String splitStr = "\t";
        StringBuilder partsSql = new StringBuilder("insert into parts_info(parts_id,catg_id,parts_name,short_name,prices,ord,create_at,creator) values ");
        StringBuilder supportSql = new StringBuilder("insert into product_support_parts(support_id,product_id,parts_catg_id,parts_id,standard) values ");
        StringBuilder preSql = new StringBuilder("insert into product_pre_cto_parts(pre_cto_parts_id,pre_cto_id,parts_id) values ");
        int ord = 1;
        for (String str : list){
            String[] strArr = str.split(splitStr);
            String type = strArr[0];
            String catg_id = null;
            if(keyList.contains(type)){
                catg_id = String.valueOf(keyList.indexOf(type) + 1);
            }
            String parts_id = UUIDUtil.getUUID();
            String parts_name = strArr[1];
            parts_name = parts_name.replaceAll("'","''");
            String short_name = strArr[2];
            short_name = short_name.replaceAll("'","''");
            Double prices = Double.valueOf(strArr[3]);
            partsSql.append("\n,('").append(parts_id).append("','").append(catg_id).append("','").append(parts_name).append("','").append(short_name).append("',").append(prices).append(",").append(ord).append(",now(),'system')");

            Integer standard = Integer.valueOf(strArr[4]);
            supportSql.append("\n,('").append(UUIDUtil.getUUID()).append("','1','").append(catg_id).append("','").append(parts_id).append("',").append(standard).append(")");

            String preName = strArr[5];
            if (preKeyList.contains(preName)){
                String pre_cto_id = String.valueOf(preKeyList.indexOf(preName) + 1);
                preSql.append("\n,('").append(UUIDUtil.getUUID()).append("','").append(pre_cto_id).append("','").append(parts_id).append("')");
            }
        }
        System.out.println(partsSql);
        System.out.println(supportSql);
        System.out.println(preSql);



//        importExcel();
    }

    public static void importExcel() throws Exception {
        File file = new File(filePath);
        if(file.exists()){
            InputStream inputStream = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            //跳过首行读取数据
            int rowIndex = 0;
            List<List<Object>> dataList = new ArrayList<List<Object>>();
            for (Row row : sheet){
//                if(rowIndex < 1){
//                    continue;
//                }
                List<Object> tempList = new ArrayList<Object>();
                tempList.add(getCellStringValue(row.getCell(0)));
                tempList.add(getCellStringValue(row.getCell(1)));
                tempList.add(getCellStringValue(row.getCell(2)));
                tempList.add(getCellStringValue(row.getCell(3)));
                tempList.add(getCellStringValue(row.getCell(4)));
                tempList.add(getCellDoubleValue(row.getCell(5)));
                tempList.add(getCellStringValue(row.getCell(6)));
                tempList.add(getCellStringValue(row.getCell(7)));
                tempList.add(getCellStringValue(row.getCell(8)));
                tempList.add(getCellStringValue(row.getCell(9)));
                tempList.add(getCellStringValue(row.getCell(10)));
                tempList.add(getCellDoubleValue(row.getCell(11)));
                tempList.add(getCellStringValue(row.getCell(12)));
                tempList.add(getCellStringValue(row.getCell(13)));
                dataList.add(tempList);
                rowIndex ++;
            }
            System.out.println(JSON.toJSONString(dataList));
        }
    }

    public static String getCellStringValue(Cell cell){
        if(cell != null){
            return cell.getStringCellValue();
        }else{
            return null;
        }
    }

    public static String getCellDoubleValue(Cell cell){
        return getCellStringValue(cell);
    }
//    public static Double getCellDoubleValue(Cell cell){
//        if(cell != null){
//            return cell.getNumericCellValue();
//        }else{
//            return null;
//        }
//    }

}
