package com.zhw.core.poi;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;
 
 
public class ExcelShower  {
 
    /**
     * SID.
     */
    private static final long serialVersionUID = -8344971443770122206L;
    
    public class RowColSpan{
    	public int rowstartindex;
    	public int colstartindex;
    	public int rowspan;
    	public int colspan;
    	
		public RowColSpan() {
			super();
		}

		public RowColSpan(int rowstartindex, int colstartindex, int rowspan,
				int colspan) {
			super();
			this.rowstartindex = rowstartindex;
			this.colstartindex = colstartindex;
			this.rowspan = rowspan;
			this.colspan = colspan;
		}
    	
    }
 
    /**
     * 读取 Excel 显示页面.
     * @param filename
     * @return
     * @throws Exception
     */
    public StringBuffer read(String  filename) throws Exception {
        HSSFSheet sheet = null;
        StringBuffer lsb = new StringBuffer();
        String cr = "\n";
        int whiteRowheight = 17;
        String excelFileName = filename;
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(excelFileName)); // 获整个Excel
 
            for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
                sheet = workbook.getSheetAt(sheetIndex);// 获所有的sheet
                String sheetName = workbook.getSheetName(sheetIndex); // sheetName
                if (workbook.getSheetAt(sheetIndex) != null) {
                    sheet = workbook.getSheetAt(sheetIndex);// 获得不为空的这个sheet
                    if (sheet != null) {
                    	whiteRowheight = (int) sheet.getDefaultRowHeightInPoints();
                    	
                        int firstRowNum = sheet.getFirstRowNum(); // 第一行
                        int lastRowNum = sheet.getLastRowNum(); // 最后一行
                        
                        ArrayList<RowColSpan> rcList = new ArrayList<RowColSpan>();
                        // 构造Table
                        lsb.append("<table width=\"100%\" style=\"margin:2px 0 2px 0;border-collapse:collapse;\">"+cr);//border:1px solid #000;border-width:1px 0 0 1px;
                        for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
                            if (sheet.getRow(rowNum) != null) {// 如果行不为空，
                                HSSFRow row = sheet.getRow(rowNum);
                                short firstCellNum = row.getFirstCellNum(); // 该行的第一个单元格
                                short lastCellNum = row.getLastCellNum(); // 该行的最后一个单元格
                                int height = (int) (row.getHeight()/15.625); // 行的高度
                                
                                lsb.append("<tr height=\""+height+"\" style=\"margin:2px 0 2px 0;\">"+cr);//border:1px solid #000;border-width:0 1px 1px 0;
                                for (short cellNum = firstCellNum; cellNum <= lastCellNum; cellNum++) { // 循环该行的每一个单元格    
                                	HSSFCell cell = row.getCell(cellNum);
                                    if (cell != null) {
                                    	HSSFCellStyle cellStyle = cell.getCellStyle();
                                        HSSFPalette palette = workbook.getCustomPalette(); //类HSSFPalette用于求颜色的国际标准形式
                                        HSSFColor hColor = palette.getColor(cellStyle.getFillForegroundColor());
                                        HSSFColor hColor2 = palette.getColor(cellStyle.getFont(workbook).getColor());
                                    	int cellReginCol = getMergerCellRegionCol(sheet,rowNum, cellNum); // 合并的列（solspan）
                                        int cellReginRow = getMergerCellRegionRow(sheet, rowNum, cellNum);// 合并的行（rowspan）
                                    	// 获取边框和颜色
                                        Short bct = cellStyle.getTopBorderColor();
                                        Short bcr = cellStyle.getRightBorderColor();
                                        Short bcb = cellStyle.getBottomBorderColor();
                                        Short bcl = cellStyle.getLeftBorderColor();
                                        Short bwt = cellStyle.getBorderTop();
                                        Short bwr = cellStyle.getBorderRight();
                                        Short bwb = cellStyle.getBorderBottom();
                                        Short bwl = cellStyle.getBorderLeft();
                                        String bctn = convertToStardColor(palette.getColor(bct));
                                        String bcrn = convertToStardColor(palette.getColor(bcr));
                                        String bcbn = convertToStardColor(palette.getColor(bcb));
                                        String bcln = convertToStardColor(palette.getColor(bcl));   
//                                        System.out.println(sheet.getColumnOutlineLevel(cellNum));
//                                        short fontHeights = (short) (cellStyle.getFont(workbook).getFontHeight() / 2); // 字体大小
//                                        float f = sheet.getColumnWidthInPixels(cellNum);
//                                        short fontHeightp = (short) (cellStyle.getFont(workbook).getFontHeightInPoints() / 2); // 字体大小
//                                        int width1 = sheet.getColumnWidth(cellNum); //
//                                        float width2 = sheet.getColumnWidthInPixels(cellNum); //
                                        // 合并单元格，获取第二个开始为CELL_TYPE_BLANK，或者有边框的空白单元格
                                        if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                                            //判断是否是合并单元格，如果已经在合并单元格内，则不画表格
                                            if(cellReginRow>1 || cellReginCol>1){
                                        		boolean isinblock = false;
                                        		for (int i = 0; i < rcList.size(); i++) {
                                        			RowColSpan each = rcList.get(i);
													if(each.rowstartindex<=rowNum && each.rowstartindex+each.rowspan>rowNum &&
															each.colstartindex<=cellNum && each.colstartindex+each.colspan>cellNum){
														isinblock = true;
														break;
													}
												}
                                        		if(isinblock){
                                        			continue;
                                        		}
                                        	}
                                        	String borderw = String.format("border-width:%dpx %dpx %dpx %dpx;", bwt,bwr,bwb,bwl);
                                        	String borderc = getBorderColor(bctn,bcrn,bcbn,bcln);
                                            StringBuffer tdStyle =new StringBuffer("<td style=\"border-style:solid; "+borderw+borderc+"margin:2px 0 2px 0; "+cr);

                                            String bgColor = convertToStardColor(hColor);//背景颜色
                                            short boldWeight = cellStyle.getFont(workbook).getBoldweight(); // 字体粗细
                                            short fontHeight = (short) (cellStyle.getFont(workbook).getFontHeight() / 2); // 字体大小
                                            String fontColor = convertToStardColor(hColor2); // 字体颜色
                                            if(bgColor != null && !"".equals(bgColor.trim())){
                                                tdStyle.append(" background-color:" + bgColor + "; ");
                                            }
                                            if(fontColor != null && !"".equals(fontColor.trim())) {
                                                tdStyle.append(" color:" + fontColor + "; ");
                                            }
                                            tdStyle.append(" font-weight:" + boldWeight + "; ");
                                            tdStyle.append(" font-size: " + fontHeight + "%;"); 
                                            lsb.append(tdStyle + "\"");
                                            
                                            int width = (int) (sheet.getColumnWidthInPixels(cellNum));//(sheet.getColumnWidth(cellNum)/ 35.7); //                                            
                                            String align = convertAlignToHtml(cellStyle.getAlignment()); // 
                                            String vAlign = convertVerticalAlignToHtml(cellStyle.getVerticalAlignment());
                                            lsb.append(" align=\"" + align + "\" valign=\""+vAlign+"\" width=\"" + width + "\" ");
                                            lsb.append(" colspan=\"" + 0 + "\" rowspan=\""+0+"\"");
                                            lsb.append(">"+getCellValue(cell)+cr+"</td>"+cr);
                                            continue;
                                        }

//                                            StringBuffer tdStyle =new StringBuffer("<td style=\"border:1px solid #000; border-width:0 1px 1px 0;margin:2px 0 2px 0; "+cr);
                                        //判断是否是合并单元格，如果已经在合并单元格，右边框可能取到为0，设置为合并单元格最后一个单元格的有边框值
	                                    if(cellReginRow>1 || cellReginCol>1){
	                                    	if(bwr <= 1){
		                                    	HSSFCell lastCopsedcell = row.getCell(cellNum+cellReginCol-1);
		                                    	if(lastCopsedcell!=null)
		                                    	bwr = lastCopsedcell.getCellStyle().getBorderRight();
	                                    	}
	                                    }
                                    	String borderw = String.format("border-width:%dpx %dpx %dpx %dpx;", bwt,bwr,bwb,bwl);
                                    	String borderc = getBorderColor(bctn,bcrn,bcbn,bcln);
                                        StringBuffer tdStyle =new StringBuffer("<td style=\"border-style:solid; "+borderw+borderc+"margin:2px 0 2px 0; "+cr);
                                        
                                        String bgColor = convertToStardColor(hColor);//背景颜色
                                        short boldWeight = cellStyle.getFont(workbook).getBoldweight(); // 字体粗细
                                        short fontHeight = (short) (cellStyle.getFont(workbook).getFontHeight() / 2); // 字体大小
                                        String fontColor = convertToStardColor(hColor2); // 字体颜色
                                        if(bgColor != null && !"".equals(bgColor.trim())){
                                            tdStyle.append(" background-color:" + bgColor + "; ");
                                        }
                                        if(fontColor != null && !"".equals(fontColor.trim())) {
                                            tdStyle.append(" color:" + fontColor + "; ");
                                        }
                                        tdStyle.append(" font-weight:" + boldWeight + "; ");
                                        tdStyle.append(" font-size: " + fontHeight + "%;"); 
                                        lsb.append(tdStyle + "\"");
                                         
                                        int width = (int) (sheet.getColumnWidthInPixels(cellNum));//(sheet.getColumnWidth(cellNum)/ 35.7); // 
                                        String align = convertAlignToHtml(cellStyle.getAlignment()); // 
                                        String vAlign = convertVerticalAlignToHtml(cellStyle.getVerticalAlignment());
                                        lsb.append(" align=\"" + align + "\" valign=\""+vAlign+"\" width=\"" + width + "\" ");
                                        lsb.append(" colspan=\"" + cellReginCol + "\" rowspan=\""+cellReginRow+"\"");
                                        lsb.append(">"+getCellValue(cell)+cr+"</td>"+cr);
                                        //判断是否是合并单元格，如果已经在合并单元格，记录并给后面留作判断
                                        if(cellReginRow>1 || cellReginCol>1){
                                        	RowColSpan e = new RowColSpan(rowNum,cellNum,cellReginRow,cellReginCol);
											rcList.add(e);
                                        }
                                    }else{//  cell==null,插入空 列
                                    	lsb.append("<td style=\"margin:2px 0 2px 0;\">"+cr+"</td>"+cr);
                                    }
                                }
                                lsb.append("</tr>"+cr);
                            }
                            else{// row==null,插入空行
                            	lsb.append("<tr height=\""+whiteRowheight+"\" style=\"margin:2px 0 2px 0;\">"+cr+"</tr>"+cr);//border:1px solid #000;border-width:0 1px 1px 0;
                            }
                        }
                        lsb.append("</table>"+cr);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("文件 " + excelFileName + " 没有找到!");
        } catch (IOException e) {
            throw new FileNotFoundException("文件 " + excelFileName + " 处理错误("
                    + e.getMessage() + ")!");
        }
        return lsb;
    }
 
	/**
     * 取得单元格的值
     * @param cell
     * @return
     * @throws IOException
     */
    private static Object getCellValue(HSSFCell cell) throws IOException {
        Object value = "";
        if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
            value = cell.getRichStringCellValue().toString();
        } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                Date date = cell.getDateCellValue();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                value = sdf.format(date);
            } else {
                double value_temp = (double) cell.getNumericCellValue();
                BigDecimal bd = new BigDecimal(value_temp);
                BigDecimal bd1 = bd.setScale(3, bd.ROUND_HALF_UP);
                value = bd1.doubleValue();
                 
                /*
                DecimalFormat format = new DecimalFormat("#0.###");
                value = format.format(cell.getNumericCellValue());
                */
            }
        }
        if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
            value = "";
        }
        return value;
    }
     
    /**
     * 判断单元格在不在合并单元格范围内，如果是，获取其合并的列数。
     * @param sheet 工作表
     * @param cellRow 被判断的单元格的行号
     * @param cellCol 被判断的单元格的列号
     * @return
     * @throws IOException
     */
    private static int getMergerCellRegionCol(HSSFSheet sheet, int cellRow,int cellCol) throws IOException {
        int retVal = 0;
        int sheetMergerCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergerCount; i++) {
            CellRangeAddress cra = (CellRangeAddress) sheet.getMergedRegion(i);
            int firstRow = cra.getFirstRow();  // 合并单元格CELL起始行
            int firstCol = cra.getFirstColumn(); // 合并单元格CELL起始列
            int lastRow = cra.getLastRow(); // 合并单元格CELL结束行
            int lastCol = cra.getLastColumn(); // 合并单元格CELL结束列
            if (cellRow >= firstRow && cellRow <= lastRow) { // 判断该单元格是否是在合并单元格中
                if (cellCol >= firstCol && cellCol <= lastCol) {
                    retVal = lastCol - firstCol+1; // 得到合并的列数
                    break;
                }
            }
        }
        return retVal;
    }
     
    /**
     * 判断单元格是否是合并的单格，如果是，获取其合并的行数。
     * @param sheet 表单
     * @param cellRow 被判断的单元格的行号
     * @param cellCol 被判断的单元格的列号 
     * @return
     * @throws IOException
     */
    private static int getMergerCellRegionRow(HSSFSheet sheet, int cellRow,int cellCol) throws IOException {
        int retVal = 0;
        int sheetMergerCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergerCount; i++) {
            CellRangeAddress cra = (CellRangeAddress) sheet.getMergedRegion(i);
            int firstRow = cra.getFirstRow();  // 合并单元格CELL起始行
            int firstCol = cra.getFirstColumn(); // 合并单元格CELL起始列
            int lastRow = cra.getLastRow(); // 合并单元格CELL结束行
            int lastCol = cra.getLastColumn(); // 合并单元格CELL结束列
            if (cellRow >= firstRow && cellRow <= lastRow) { // 判断该单元格是否是在合并单元格中
                if (cellCol >= firstCol && cellCol <= lastCol) {
                    retVal = lastRow - firstRow + 1; // 得到合并的行数
                    break;
                }
            }
        }
        return retVal;
    }
     
    /**
     * 单元格背景色转换
     * @param hc
     * @return
     */
    private String convertToStardColor(HSSFColor hc) {
        StringBuffer sb = new StringBuffer("");
        if (hc != null) {
            int a = HSSFColor.AUTOMATIC.index;
            int b = hc.getIndex();
            if (a == b) {
                return null;
            }
            sb.append("#");
            for (int i = 0; i < hc.getTriplet().length; i++) {
                String str ;
                String str_tmp = Integer.toHexString(hc.getTriplet()[i]);
                if (str_tmp != null && str_tmp.length() < 2) {
                    str = "0" + str_tmp;
                }else {
                    str = str_tmp;
                }
                sb.append(str);
            }
        }
        return sb.toString();
    }
     
    /**
     * 单元格小平对齐
     * @param alignment
     * @return
     */
    private String convertAlignToHtml(short alignment) {
        String align = "left";
        switch (alignment) {
        case HSSFCellStyle.ALIGN_LEFT:
            align = "left";
            break;
        case HSSFCellStyle.ALIGN_CENTER:
            align = "center";
            break;
        case HSSFCellStyle.ALIGN_RIGHT:
            align = "right";
            break;
        default:
            break;
        }
        return align;
    }
     
    /**
     * 单元格垂直对齐
     * @param verticalAlignment
     * @return
     */
    private String convertVerticalAlignToHtml(short verticalAlignment) {
        String valign = "middle";
        switch (verticalAlignment) {
        case HSSFCellStyle.VERTICAL_BOTTOM:
            valign = "bottom";
            break;
        case HSSFCellStyle.VERTICAL_CENTER:
            valign = "center";
            break;
        case HSSFCellStyle.VERTICAL_TOP:
            valign = "top";
            break;
        default:
            break;
        }
        return valign;
    }
    
    private String getBorderColor(String bctn, String bcrn, String bcbn,
			String bcln) {
    	String defaultbc = "#000";
    	if(bctn == null || "".equals(bctn.trim())){
    		bctn=defaultbc;
        }
    	if(bcrn == null || "".equals(bcrn.trim())){
    		bcrn=defaultbc;
        }
    	if(bcbn == null || "".equals(bcbn.trim())){
    		bcbn=defaultbc;
        }
    	if(bcln == null || "".equals(bcln.trim())){
    		bcln=defaultbc;
        }
    	String format = "border-color:%s %s %s %s;";
		return String.format(format, bctn.trim(),bcrn.trim(),bcbn.trim(),bcln.trim());
	}
    
    
    public static void main(String [] args) throws Exception{
    	ExcelShower e = new ExcelShower();
    	StringBuffer sb = e.read(null);
    	System.out.println(sb.toString());
    }
}