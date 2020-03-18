package com.kfa.bank.entity;

import javax.persistence.*;
import java.util.Date;

@Entity

@Table (name="myfiles")
public class CustomFile {

	@Id
	@Column(name="id", nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="filename", length=150, nullable=false)
	private String filename;

	@Column(name="extension")
	private String extension;

	@Column(name="folderid", nullable=false)
	//@ManyToOne
	//@JoinColumn(name = "folderId", referencedColumnName = "id")
	private int folderid;

	@Column(name="size")
	private int size;

	@Column(name="absolutepath")
	private String absolutepath;

	@Column(name="creationdate")
	private Date creationdate;

	@Column(name="updatedate")
	private Date updatedate;


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}


	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}


	/**
	 * @return the folderId
	 */
	public int getFolderId() {
		return folderid;
	}


	/**
	 * @param folderId the folderId to set
	 */
	public void setFolderId(int folderId) {
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

	public void setUpdatedate(Date updateDate) {
		this.updatedate = updatedate;
	}
}
