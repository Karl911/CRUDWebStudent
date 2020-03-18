package com.kfa.bank.model;

import java.util.Date;

public class CustomFileInfo {

   private Long id;

   private String filename;
   private String extension;
   private int folderid;
   private int size;
   private String absolutepath;
   private Date creationdate;
   private Date updatedate;

   public CustomFileInfo() {

   }

   // Used in JPA query.
   public CustomFileInfo(Long id, String filename, String extension, int folderId, int size, String absolutePath, Date creationDate, Date updateDate) {
      this.id = id;
      this.filename = filename;
      this.extension = extension;
      this.folderid = folderId;
      this.size = size;
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

   public void setAbsolutepath(String absolutePath){ this.absolutepath = absolutePath; }

   public int getSize() {
      return size;
   }

   public void setSize(int size) {
      this.size = size;
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
 

}
