package cn.wangjy.catcher.service;

import cn.wangjy.catcher.bean.AmazonComment;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * EXCEL导出工具类
 *
 * @author Sunny
 * @create 2017-07-21 10:05
 **/
public class ExportExcelService {

    /**
     * 页面导出默认表头
     */
<<<<<<< HEAD
    private static final String[] HEADERS = {"星星", "用户名", "时间", "颜色", "尺寸", "是否确认", "评论"};
=======
    private static final String[] HEADERS = {"星星", "用户名", "时间", "尺寸", "颜色", "确认购买", "评论"};
>>>>>>> c6927df8cd7f016b530eb5f65601f35725760f4a


    /**
     * 用于导出
     *
     * @param title
     * @param out
     */
    public static void exportExcel(String title, OutputStream out, List<AmazonComment> dataList) {
        // 声明一个工作薄
        HSSFWorkbook wb = new HSSFWorkbook();
        // 声明一个单子并命名
        HSSFSheet sheet = wb.createSheet(title);
        if (null != HEADERS) {
            //绘制表头
            drawHeader(wb, sheet, HEADERS);

            //填充数据
            fillData(wb, sheet, HEADERS, dataList);
        }
        try {
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置默认样式
     *
     * @param wb
     * @param sheet
     * @return
     */
    private static HSSFCellStyle setDefaultStyle(HSSFWorkbook wb, HSSFSheet sheet) {
        // 给单子名称一个长度
        sheet.setDefaultColumnWidth((short) 10);
        // 生成一个样式
        HSSFCellStyle style = wb.createCellStyle();
        //设置字体样式
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 14);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setFont(font);
        style.setWrapText(false);
        //设置边框设置
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框类型
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框类型
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框类型
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框类型

        return style;
    }

    /**
     * 设置特殊样式
     *
     * @param wb
     * @param sheet
     * @param type
     * @return
     */
    private static HSSFCellStyle setSpecialStyle(HSSFWorkbook wb, HSSFSheet sheet, int type) {
        HSSFCellStyle specialStyle = setDefaultStyle(wb, sheet);
        HSSFFont font = specialStyle.getFont(wb);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        switch (type) {
            case 1:
                specialStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                specialStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
                specialStyle.setFont(font);
                break;
            case 2:
                specialStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                specialStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
                specialStyle.setFont(font);
                break;
            case 3:
                specialStyle.setFont(font);
                break;
            default:
                break;
        }
        return specialStyle;
    }

    /**
     * 绘制表头
     *
     * @param wb
     * @param sheet
     * @param headers
     */
    private static void drawHeader(HSSFWorkbook wb, HSSFSheet sheet, String[] headers) {
        //设置基本样式
        HSSFCellStyle style = setDefaultStyle(wb, sheet);
        HSSFRow row = sheet.createRow(0);
        int totalColumn = headers.length;
        for (int i = 0; i < totalColumn; i++) {
            HSSFCell cell = row.createCell(i);
            //特殊样式设置
            HSSFCellStyle specialStyle = setSpecialStyle(wb, sheet, 1);
            cell.setCellStyle(specialStyle);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);

        }
    }


    /**
     * 填充数据
     *
     * @param wb
     * @param sheet
     * @param dataList
     */
    private static void fillData(HSSFWorkbook wb, HSSFSheet sheet, String[] headers, List<AmazonComment> dataList) {
        //设置基本样式
        HSSFCellStyle style = setDefaultStyle(wb, sheet);
        int totalColumn = headers.length;

        for (int j = 0; j < dataList.size(); j++) {
            HSSFRow row = sheet.createRow(j + 1);
            for (int column = 0; column < totalColumn; column++) {
                HSSFCell cell = row.createCell(column);
                cell.setCellStyle(style);
                if (column == 0) {
                    HSSFRichTextString text = new HSSFRichTextString(dataList.get(j).getStarts());
                    cell.setCellValue(text);
                }
                if (column == 1) {
                    HSSFRichTextString text = new HSSFRichTextString(dataList.get(j).getAuthor());
                    cell.setCellValue(text);
                }
                if (column == 2) {
                    HSSFRichTextString text = new HSSFRichTextString(dataList.get(j).getDate());
                    cell.setCellValue(text);
                }
                if (column == 3) {
<<<<<<< HEAD
                    HSSFRichTextString text = new HSSFRichTextString(dataList.get(j).getColor());
                    cell.setCellValue(text);
                }
                if (column == 4) {
                    HSSFRichTextString text = new HSSFRichTextString(dataList.get(j).getSize());
=======
                    HSSFRichTextString text = new HSSFRichTextString(dataList.get(j).getSize());
                    cell.setCellValue(text);
                }
                if (column == 4) {
                    HSSFRichTextString text = new HSSFRichTextString(dataList.get(j).getColor());
>>>>>>> c6927df8cd7f016b530eb5f65601f35725760f4a
                    cell.setCellValue(text);
                }
                if (column == 5) {
                    HSSFRichTextString text = new HSSFRichTextString(dataList.get(j).getVerified());
                    cell.setCellValue(text);
                }
                if (column == 6) {
                    HSSFRichTextString text = new HSSFRichTextString(dataList.get(j).getComment());
                    cell.setCellValue(text);
                }

            }
        }
    }


    public static void main(String[] args) {
        File fileTmp = new File("aa.xls");
        FileOutputStream fileOutStream = null;
        List<AmazonComment> list = new ArrayList<>();
<<<<<<< HEAD
        AmazonComment data = new AmazonComment();
        data.setAuthor("wangjy");
        data.setComment("test");
        data.setStarts("3");
        data.setDate("2017");
        data.setSize("234");
        data.setColor("red");
        data.setVerified("verified");

        list.add(data);
        list.add(data);
        list.add(data);
        list.add(data);
=======
        AmazonComment date = new AmazonComment();
        date.setStarts("start");
        date.setAuthor("author");
        list.add(date);
        list.add(date);
        list.add(date);
        list.add(date);
>>>>>>> c6927df8cd7f016b530eb5f65601f35725760f4a
        try {
            fileTmp.createNewFile();
            fileOutStream = new FileOutputStream(fileTmp);
//            String[] headers = {"总数", "192.168.1.2", "192.168.1.3"};
//            String[] headers1 = {"总数", "192.168.1.2"};

            exportExcel("2017-10-25", fileOutStream, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
