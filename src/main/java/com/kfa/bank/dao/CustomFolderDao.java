package com.kfa.bank.dao;

import com.kfa.bank.entity.CustomFile;
import com.kfa.bank.entity.CustomFolder;
import com.kfa.bank.exception.CustomFileTransactionException;
import com.kfa.bank.model.CustomFileInfo;
import com.kfa.bank.model.CustomFolderInfo;
import org.hibernate.loader.criteria.CriteriaQueryTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;

@Repository
public class CustomFolderDao {

	@Autowired
	private EntityManager entityManager;

	public CustomFolderDao() {

	}


	public CustomFolder findById(Long id)
	{
		return this.entityManager.find(CustomFolder.class, id);
	}

	public CustomFolderInfo findCustomFolderByFolderpath (String folderpath){

		String sql = "Select new "+ CustomFolder.class.getName() //
				+ "(f.id, f.idparent, f.foldername, f.folderpath, f.size, f.creationdate, f.updatedate )" //
				+" from "+CustomFolder.class.getName() + " As f"
				+" where f.folderpath='"+folderpath+"'";

	  //	select * from myfolders where folderpath= 'E:\\Music\\mp3\\Bandes Originales'

		Query query = entityManager.createQuery(sql, CustomFolderInfo.class);

		return (CustomFolderInfo) query.getSingleResult();

		//return this.entityManager.find(CustomFolder.class, folderpath);
	}

	public List<CustomFileInfo> getFolders() {
		String sql = "Select new " + CustomFolderInfo.class.getName() //
				+ "(e.id,e.foldername,e.idparent, e.folderpath, e.size, e.creationdate, e.updatedate) " //
				+ " from " + CustomFolder.class.getName() + " As e ";
		Query query = entityManager.createQuery(sql, CustomFolderInfo.class);
		return query.getResultList();
	}


	@Transactional(propagation = Propagation.REQUIRES_NEW,
			rollbackFor = CustomFileTransactionException.class)
	public void saveFolder(String folderName, int idParent, String folderPath, int size, Date creationDate, Date updateDate) throws CustomFileTransactionException {

		CustomFolder folder = new CustomFolder(idParent, folderName, folderPath, size, creationDate, updateDate);
		/*
		folder.setIdparent(idParent);
		folder.setFoldername(folderName);
		folder.setFolderpath(folderPath);
		folder.setSize(size);
		folder.setCreationdate(null);
		folder.setUpdatedate(null);
		 */
		this.entityManager.persist(folder);
		//this.entityManager.flush();

	}


	//@Test
	/*
	public void testSaveFolder() {

		CustomFolder folder = new CustomFolder();
		folder.setFoldername("toto");
		folder.setIdparent(1);
		folder.setFolderpath("E:\\music");
		folder.setSize(10L);
		folder.setCreationdate(new Date());
		folder.setUpdatedate(new Date());
				//setCustomer(customerService.findByName("John"));

		this.entityManager.persist(folder);
	}
	/*
	@Transactional(propagation = Propagation.REQUIRES_NEW,
			rollbackFor = CustomFileTransactionException.class)
	public void saveFolder(String folderName, int idParent, String folderpath, int size, Date creationDate, Date updateDate) throws CustomFileTransactionException {

		CustomFolder folder = new CustomFolder();

		folder.setFoldername(folderName);
		folder.setIdparent(idParent);
		folder.setFolderpath(folderpath);
		folder.setSize(size);
		folder.setCreationdate(null);
		folder.setUpdatedate(null);
		this.entityManager.persist(folder);
		//this.entityManager.flush();

	}
	*/

}
