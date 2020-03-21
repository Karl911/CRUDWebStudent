package com.kfa.bank.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity

@Table (name="myfolders")
public class CustomFolder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2738248747016922169L;

	@Id
	@Column(name="id", nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="idparent", nullable=false)
	private int idparent;

	@Column(name="foldername", length=150, nullable=false)
	private String foldername;

	@Column(name="folderpath", length=150, nullable=false)
	private String folderpath;

	@Column(name="size")
	private int size;

	@Column(name="creationdate")
	private Date creationdate;

	@Column(name="updatedate")
	private Date updatedate;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getIdparent() {	return idparent;}
	public void setIdparent(int idparent) {this.idparent = idparent;}

	public String getFoldername() {	return foldername;}
	public void setFoldername(String foldername) {	this.foldername = foldername;}

	public String getFoldepath() {	return folderpath;}
	public void setFolderpath(String folderPath) {	this.folderpath = folderPath;}

	public int getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = (int) size;
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
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public CustomFolder() {

	}
	public CustomFolder (/*int id,*/ int idparent, String foldername, String folderpath, int size, Date creationdate, Date updatedate) {
		//this.id = id;
		this.idparent = idparent;
		this.foldername = foldername;
		this.folderpath = folderpath;
		this.size = size;
		this.creationdate = creationdate;
		this.updatedate = updatedate;
	}
}
