package com.sykean.hmhc.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lee on 2015/1/21.
 */
public class ExcelUtils {
    private static Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    private static XSSFFont getRedStar(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.RED.index);
        return font;
    }

    /**
     * 导入
     *
     * @param ism
     * @return
     * @throws Exception
     */
    public static List<String[]> imports(InputStream ism, int length) throws Exception {

        /**
         * 定义流
         */
        List<String[]> mainList = null;
        try {

            // HSSFWorkbook workbook = new HSSFWorkbook(fs);
            Workbook workbook = null;
            try {
                workbook = new XSSFWorkbook(ism);
            } catch (Exception ex) {
                POIFSFileSystem fs = new POIFSFileSystem(ism);
                workbook = new HSSFWorkbook(fs);
            }
            /**
             * 保存集合
             */
            Sheet sheet = workbook.getSheetAt(0);
            mainList = new ArrayList<String[]>(sheet.getPhysicalNumberOfRows());
            for (int i = 2; i < sheet.getPhysicalNumberOfRows(); i++) {
                /**
                 * 行
                 */
                Row row = sheet.getRow(i);
                String[] subList = new String[length];
                for (int j = 0; j < length; j++) {
                    String cellValue = null;
                    Cell cell = row.getCell(j);
                    if (cell != null) {
                        cellValue = changeType(cell);
                    }
                    subList[j] = cellValue;
                }
                /**
                 *  添加
                 */
                mainList.add(subList);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            mainList = null;
        }
        return mainList;
    }

    private static String changeType(Cell cell) {
        String cellValue = null;
        try {
            if (cell != null) {
                logger.debug(cell.getCellType() + "-");
                /**
                 * 判断当前Cell的Type
                 */
                switch (cell.getCellType()) {
                    /**
                     * 如果当前Cell的Type为NUMERIC
                     */
                    case Cell.CELL_TYPE_NUMERIC:
                        double tnumeric = cell.getNumericCellValue();
                        if ((tnumeric - (long) tnumeric) > 0) {
                            cellValue = String.valueOf(cell.getNumericCellValue());
                        } else {
                            cellValue = String.valueOf((long) tnumeric);
                        }
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellValue = sdf.format(date);
                        break;
                    /**
                     * 如果当前Cell的Type为STRING
                     */
                    case Cell.CELL_TYPE_STRING:
                        cellValue = cell.getRichStringCellValue().getString();
                        break;

                    /**
                     * 默认的Cell值
                     */
                    default:
                        cellValue = "";
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        return cellValue;
    }

    public static byte[] export(List<String[]> list) {
        long startTime = System.currentTimeMillis();
        XSSFWorkbook workbook = null;
        try {
            //			workbook = new HSSFWorkbook();
            workbook = new XSSFWorkbook();

            //第一个sheet
            Sheet sheet = workbook.createSheet();
            int i = 0;
            for (Object[] item : list) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    row = sheet.createRow(i);
                }
                int j = 0;
                for (Object item2 : item) {
                    Cell cell = row.getCell(j);
                    if (cell == null) {
                        cell = row.createCell(j);
                    }
                    setValue(cell, item2);
                    j++;
                }
                i++;
            }
            //auto width
            String[] arr = list.get(0);
            for (int x = 0; x < arr.length; x++) {
                sheet.setColumnWidth((short) x, arr[x].getBytes().length * 512);
            }

            // OutputStream
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workbook.write(os);
            byte[] bytes = os.toByteArray();
            System.out.println(System.currentTimeMillis() - startTime + "导出时间");
            return bytes;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static void setValue(Cell cell, Object object) {
        if (object == null) {
            return;
        }
        if (object instanceof Boolean) {
            cell.setCellValue((Boolean) object);
        } else if (object instanceof Byte) {
            cell.setCellValue((Byte) object);
        } else if (object instanceof Integer) {
            cell.setCellValue((Integer) object);
        } else if (object instanceof Long) {
            cell.setCellValue((Long) object);
        } else if (object instanceof Float) {
            cell.setCellValue((Float) object);
        } else if (object instanceof Double) {
            cell.setCellValue((Double) object);
        } else if (object instanceof String) {
            cell.setCellValue((String) object);
        } else if (object instanceof Date) {
            cell.setCellValue((Date) object);
        } else {
            cell.setCellValue(object.toString());
        }
    }

    private static void setValue(Cell cell, Object object, XSSFFont font) {
        if (object == null) {
            return;
        }
        if (object instanceof Boolean) {
            cell.setCellValue((Boolean) object);
        } else if (object instanceof Byte) {
            cell.setCellValue((Byte) object);
        } else if (object instanceof Integer) {
            cell.setCellValue((Integer) object);
        } else if (object instanceof Long) {
            cell.setCellValue((Long) object);
        } else if (object instanceof Float) {
            cell.setCellValue((Float) object);
        } else if (object instanceof Double) {
            cell.setCellValue((Double) object);
        } else if (object instanceof String) {
            if (object.toString().contains("*")) {
                XSSFRichTextString ts = new XSSFRichTextString((String) object);
                ts.applyFont(0, 1, font);
                cell.setCellValue(ts);
            } else {
                XSSFRichTextString ts = new XSSFRichTextString((String) object);
                ts.applyFont(font);
                cell.setCellValue((String) object);
            }
        } else if (object instanceof Date) {
            cell.setCellValue((Date) object);
        } else {
            cell.setCellValue(object.toString());
        }
    }

    /**
     * 读取Excel数据解析为List
     *
     * @return
     * @throws Exception
     */
    public static List<String[]> getExcelData(MultipartFile file) throws Exception {
        if (!checkFile(file)) {
            //检查不通过
            throw new Exception("非excel文件");
        }
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<String[]> list = new ArrayList<String[]>();
        if (workbook != null) {
            int maxColumn = 0;
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet == null) {
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //循环除了第一行的所有行
                for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    //获得当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                    //获得当前行的列数
                    int lastCellNum = 0;
                    String[] cells = new String[]{};
                    if (rowNum == 0) {
                        lastCellNum = row.getLastCellNum();
                        maxColumn = lastCellNum;
                        cells = new String[row.getLastCellNum()];
                    } else {
                        lastCellNum = maxColumn;
                        cells = new String[maxColumn];
                    }
                    //循环当前行
                    for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                        Cell cell = row.getCell(cellNum);
                        cells[cellNum] = getCellValue(cell);
                    }
                    boolean isCellsEmpty = false;
                    for (String str : cells) {
                        if (StringUtils.isNotBlank(str)) {
                            isCellsEmpty = true;
                            break;
                        }
                    }
                    if (isCellsEmpty) {
                        list.add(cells);
                    }
                }
            }
        }
        if(!CollectionUtils.isEmpty(list)){
            list.remove(0);
            list.remove(0);
        }
        return list;
    }

    /**
     * 检查文件
     *
     * @param file
     * @throws IOException
     */
    public static boolean checkFile(MultipartFile file) throws IOException {
        //判断文件是否存在
        if (null == file) {
            logger.error("文件不存在！");
            return false;
        }
        //获得文件名
        String fileName = file.getOriginalFilename();
        //判断文件是否是excel文件
        if (!fileName.endsWith("xls") && !fileName.endsWith("xlsx")) {
            logger.error(fileName + "不是excel文件");
            return false;
        }
        return true;
    }

    /**
     * 通过文件获取workBook
     *
     * @param file
     * @return
     */
    public static Workbook getWorkBook(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        InputStream is = null;
        try {
            //获取excel文件的io流
            is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if (fileName.endsWith("xls")) {
                //2003
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith("xlsx")) {
                //2007 及2007以上
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return workbook;
    }

    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        //判断数据的类型
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: //数字
                cellValue = stringDateProcess(cell);
                break;
            case Cell.CELL_TYPE_STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    /**
     * 时间格式处理
     *
     * @param cell
     * @return
     */
    public static String stringDateProcess(Cell cell) {
        String result = new String();
        if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
            SimpleDateFormat sdf = null;
            if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
                sdf = new SimpleDateFormat("HH:mm");
            } else {// 日期
                sdf = new SimpleDateFormat("yyyy-MM-dd");
            }
            Date date = cell.getDateCellValue();
            result = sdf.format(date);
        } else if (cell.getCellStyle().getDataFormat() == 58) {
            // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            double value = cell.getNumericCellValue();
            Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
            result = sdf.format(date);
        } else {
            double value = cell.getNumericCellValue();
            CellStyle style = cell.getCellStyle();
            DecimalFormat format = new DecimalFormat();
            String temp = style.getDataFormatString();
            // 单元格设置成常规
            if (temp.equals("General")) {
                format.applyPattern("#");
            }
            result = format.format(value);
        }
        return result;
    }

    public static void exportFile(byte[] data, String fileName, boolean addTimestamp, HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.ms-excel");
            String userAgent = request.getHeader("User-Agent");
            fileName = fileName + (addTimestamp ? DateUtil.getTimeSimpleStr() : "") + ".xlsx";
            if (userAgent.contains("MSIE")) {//IE浏览器
                fileName = URLEncoder.encode(fileName, "UTF8");
            } else if (userAgent.contains("Mozilla")) {//google,火狐浏览器
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } else {
                fileName = URLEncoder.encode(fileName, "UTF8");//其他浏览器
            }
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            response.getOutputStream().write(data);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
