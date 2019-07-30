package utils;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtil {

    /**
     * 导出数据
     *
     * @param title    显示的导出表的标题
     * @param rowName  导出表的列名
     * @param dataList 导出的数据
     * @param response
     * @throws Exception
     */
    public static void export(String title, String[] rowName, List<Object[]> dataList, String fileSavePath, HttpServletResponse response) throws Exception {
        // 创建工作簿对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建工作表
        HSSFSheet sheet = workbook.createSheet(title);
        //sheet样式定义
        //获取列头样式对象
        HSSFCellStyle columnTopStyle = getColumnTopStyle(workbook);
        //单元格样式对象
        HSSFCellStyle style = getStyle(workbook);

        // 定义所需列数
        int columnNum = rowName.length;
        // 在索引1的位置创建行
        HSSFRow rowRowName = sheet.createRow(0);

        rowRowName.setHeight((short) (25 * 25)); //设置高度

        // 将列头设置到sheet的单元格中
        for (int n = 0; n < columnNum; n++) {
            //创建列头对应个数的单元格
            HSSFCell cellRowName = rowRowName.createCell(n);
            //设置列头单元格的数据类型
            cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);
            HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
            //设置列头单元格的值
            cellRowName.setCellValue(text);
            //设置列头单元格样式
            cellRowName.setCellStyle(columnTopStyle);
        }

        //将查询出的数据设置到sheet对应的单元格中
        for (int i = 0; i < dataList.size(); i++) {
            //遍历每个对象
            Object[] obj = dataList.get(i);
            //创建所需的行数
            HSSFRow row = sheet.createRow(i + 1);
            //设置行高
            row.setHeight((short) (25 * 20));

            for (int j = 0; j < obj.length; j++) {
                //设置单元格的数据类型
                HSSFCell cell = null;
                cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                if (!"".equals(obj[j]) && obj[j] != null) {
                    //设置单元格的值
                    if (obj[j] instanceof Date) {
                        cell.setCellValue((Date) obj[j]);
                    } else {
                        cell.setCellValue(obj[j].toString());
                    }
                }
                //设置单元格样式
                cell.setCellStyle(style);
            }
        }
        //让列宽随着导出的列长自动适应
        for (int colNum = 0; colNum < columnNum; colNum++) {
            int columnWidth = sheet.getColumnWidth(colNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                HSSFRow currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }
                if (currentRow.getCell(colNum) != null) {
                    try {
                        HSSFCell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                            int length = currentCell.getStringCellValue().getBytes().length;
                            if (columnWidth < length) {
                                columnWidth = length;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
            //设置宽度
            sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
        }
        if (workbook != null) {
            try {
                File file = new File(fileSavePath);
                if (!file.exists()){
                    file.mkdir();
                }
                FileOutputStream out = new FileOutputStream(fileSavePath + DateUtil.dateToString(new Date(), DateUtil.DATETIME_PURE_FORMAT) + "-" + title + ".xls");
                workbook.write(out);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置表头样式
     *
     * @param workbook
     * @return
     */
    private static HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short) 11);
        //字体加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        //设置单元格背景颜色
        style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        return style;

    }

    /**
     * 列数据信息单元格样式
     *
     * @param workbook
     * @return
     */
    private static HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        //font.setFontHeightInPoints((short)10);
        //字体加粗
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        return style;
    }
    /**
     * 获取表的数据
     *
     * @param file
     * @param ignoreRows
     * @return
     * @throws IOException
     */
    public static String[][] getData(File file, int ignoreRows) throws IOException {
        List<String[]> result = new ArrayList<String[]>();
        int rowSize = 0;
        // 打开HSSFWorkbook
        Workbook wb = null;
        Cell cell = null;
        Sheet st = null;
        Row row = null;
        InputStream inp = new FileInputStream(file);
        if (!inp.markSupported()) {
            inp = new PushbackInputStream(inp, 8);
        }
        //2003及以下
        if (POIFSFileSystem.hasPOIFSHeader(inp)) {
            wb = new HSSFWorkbook(inp);
            cell = (HSSFCell) cell;
            st = (HSSFSheet) st;
            row = (HSSFRow) row;
        } else if (POIXMLDocument.hasOOXMLHeader(inp)) {
            //2007及以上
            wb = new XSSFWorkbook(inp);
            cell = (XSSFCell) cell;
            st = (XSSFSheet) st;
            row = (XSSFRow) row;
        } else {
            return null;
        }
        for (int sheetIndex = 0, sheetSize = wb.getNumberOfSheets(); sheetIndex < sheetSize; sheetIndex++) {
            st = wb.getSheetAt(sheetIndex);
            // 第一行为标题，不取
            for (int rowIndex = ignoreRows,sheetRowNum = st.getLastRowNum(); rowIndex < sheetRowNum; rowIndex++) {
                row = st.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                int tempRowSize = row.getLastCellNum() + 1;
                if (tempRowSize > rowSize) {
                    rowSize = tempRowSize;
                }
                getRowData(row, result);
            }
        }
        inp.close();
        String[][] returnArray = new String[result.size()][rowSize];
        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] = (String[]) result.get(i);
        }
        return returnArray;
    }

    /**
     * 获取表头信息
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String[][] getExcelTableHeaders(File file) throws IOException {
        int rowSize = 0;
        // 打开HSSFWorkbook
        Workbook wb = null;
        Cell cell = null;
        Sheet st = null;
        Row row = null;
        InputStream inp = new FileInputStream(file);
        if (!inp.markSupported()) {
            inp = new PushbackInputStream(inp, 8);
        }
        //2003及以下
        if (POIFSFileSystem.hasPOIFSHeader(inp)) {
            wb = new HSSFWorkbook(inp);
            cell = (HSSFCell) cell;
            st = (HSSFSheet) st;
            row = (HSSFRow) row;
        } else if (POIXMLDocument.hasOOXMLHeader(inp)) {
            //2007及以上
            wb = new XSSFWorkbook(inp);
            cell = (XSSFCell) cell;
            st = (XSSFSheet) st;
            row = (XSSFRow) row;
        } else {
            return null;
        }
        List<String[]> result = new ArrayList<String[]>();
        for (int sheetIndex = 0, sheetSize = wb.getNumberOfSheets(); sheetIndex < sheetSize; sheetIndex++) {
            st = wb.getSheetAt(sheetIndex);
            row = st.getRow(0);
            getRowData(row, result);
        }
        inp.close();
        String[][] returnArray = new String[result.size()][rowSize];
        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] = result.get(i);
        }
        return returnArray;
    }

    /**
     * 获取行的内容
     *
     * @param row
     * @param result
     */
    private static void getRowData(Row row, List<String[]> result) {
        int rowSize = row.getLastCellNum() + 1;
        String[] values = new String[rowSize];
        Arrays.fill(values, "");
        boolean hasValue = false;
        for (short columnIndex = 0,eachRowCellNum = row.getLastCellNum(); columnIndex < eachRowCellNum; columnIndex++) {
            String value = "";
            Cell cell = row.getCell(columnIndex);
            if (cell != null) {
                value = getCellValue(cell, value);
            }
//            if (columnIndex == 0 && value.trim().equals("")) {
//                break;
//            }
            values[columnIndex] = rightTrim(value);
            hasValue = true;
        }
        if (hasValue) {
            result.add(values);
        }
    }

    /**
     * 获取单元格内容
     *
     * @param cell
     * @param value
     * @return
     */
    private static String getCellValue(Cell cell, String value) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    if (date != null) {
                        value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                    } else {
                        value = "";
                    }
                } else {
                    value = new DecimalFormat("0").format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_FORMULA:
                // 导入时如果为公式生成的数据则无值
                if (!cell.getStringCellValue().equals("")) {
                    value = cell.getStringCellValue();
                } else {
                    value = cell.getNumericCellValue() + "";
                }
                break;
            case Cell.CELL_TYPE_BLANK:
                break;
            case Cell.CELL_TYPE_ERROR:
                value = "";
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = (cell.getBooleanCellValue() ? "Y" : "N");
                break;
            default:
                value = "";
        }
        return value;
    }

    /**
     * 去掉字符串右边的空格
     *
     * @param str 要处理的字符串
     * @return 处理后的字符串
     */
    private static String rightTrim(String str) {
        if (str == null) {
            return "";
        }
        int length = str.length();
        for (int i = length - 1; i >= 0; i--) {
            if (str.charAt(i) != 0x20) {
                break;
            }
            length--;
        }
        return str.substring(0, length);
    }

}
