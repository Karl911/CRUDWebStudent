package com.kfa.bank.model;

import java.util.Date;

public class CustomFolderInfo {

   private Long id;

   private int idparent;
   private String foldername;
   private String folderpath;
   private int size;
   private Date creationdate;
   private Date updatedate;

   public CustomFolderInfo() {

   }

   // Used in JPA query.
   public CustomFolderInfo(Long id, int idParent, String folderName, String folderPath, int size, Date creationDate, Date updateDate) {
      this.id = id;
      this.idparent = idParent;
      this.foldername = folderName;
      this.setFolderpath(folderPath);
      this.size = size;
      this.creationdate = creationDate;
      this.updatedate = updateDate;
   }
 
   public Long getId() {
      return id;
   }
   public void setId(Long id) {
      this.id = id;
   }

   public String getFoldername() {
      return foldername;
   }
   public void setFoldername(String foldername) {
      this.foldername = foldername;
   }

   public int getIdparent() {
      return idparent;
   }
   public void setIdparent(int idParent) {
      this.idparent = idParent;
   }
   
   public String getFolderpath() {
	   return folderpath;
   }

   public void setFolderpath(String folderpath) {
	   this.folderpath = folderpath;
   }

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
