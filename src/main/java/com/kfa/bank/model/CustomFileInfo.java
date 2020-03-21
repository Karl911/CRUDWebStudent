package com.kfa.bank.model;

import java.text.DecimalFormat;
import java.util.Date;

import com.kfa.bank.utils.EnumUnit;
import com.kfa.bank.utils.SizeUnit;

public class CustomFileInfo {

   private Long id;

   private String filename;
   private String extension;
   private int folderid;
   private long fileSize;
   private SizeUnit sizeUnit;
   private String absolutepath;
   private Date creationdate;
   private Date updatedate;

   public CustomFileInfo() {

   }

   // Used in JPA query.
   public CustomFileInfo(Long id, String filename, String extension, int folderId, long fileSize, String absolutePath, Date creationDate, Date updateDate) {
      this.id = id;
      this.filename = filename;
      this.extension = extension;
      this.folderid = folderId;
      this.fileSize = fileSize;
      this.sizeUnit = getSizeUnit(fileSize);
      this.absolutepath = absolutePath;
      this.creationdate = creationDate;
      this.updatedate = updateDate;
   }
 
   public Long getId() {
      return id;
   }
 
   public void setId(Long id) {
      this.id = id;
   }

   public String getFilename() {
      return filename;
   }

   public void setFilename(String filename) {
      this.filename = filename;
   }

   public int getFolderid() {
      return folderid;
   }

   public void setFolderid(int folderId) {
      this.folderid = folderId;
   }

   public String getExtension() {
      return extension;
   }

   public void setExtension(String extension) {
      this.extension = extension;
   }

   public String getAbsolutepath() {
	   return absolutepath;
   }

   public void setAbsolutepath(String absolutePath) { 
	   this.absolutepath = absolutePath;
   }

   public long getFileSize() {
	   return fileSize;
   }	

   public void setFileSize(int fileSize) {
	   this.fileSize = fileSize;
   }

   public SizeUnit getSizeUnit() {
	   return sizeUnit;
   }

   public void setSizeUnit(SizeUnit sizeUnit) {
	   this.sizeUnit = sizeUnit;
   }

   public Date getCreationdate() {
	   return creationdate;
   }

   public void setCreationdate(Date creationDate) {
	   this.creationdate = creationDate;
   }

   public Date getUpdatedate() {
      return updatedate;
   }

   public void setUpdatedate(Date updateDate) {
      this.updatedate = updateDate;
   }
   
	public SizeUnit getSizeUnit(long fileSize){
		EnumUnit unit = EnumUnit.o;
		
		double ret = fileSize;
		int multiK = 0;
		while (ret > 1024) {
			ret /= 1024; 
			multiK ++;
		}
		switch (multiK) {
		case 1 : unit  = EnumUnit.Ko;
			break;
		case 2 : unit  = EnumUnit.Mo;
			break;
		case 3 : unit  = EnumUnit.Go;
			break;
		default:
			break;
		}
		DecimalFormat f = new DecimalFormat();
		f.setMaximumFractionDigits(1);

		String size = f.format(ret);
		SizeUnit sizeUnit = new SizeUnit(size, unit);
		return sizeUnit;
	}

}
