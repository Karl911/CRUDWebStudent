package com.kfa.bank;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kfa.bank.dao.CustomFileDao;
import com.kfa.bank.dao.CustomFolderDao;
import com.kfa.bank.entity.CustomFile;
import com.kfa.bank.entity.CustomFolder;
import com.kfa.bank.exception.CustomFileTransactionException;
import com.kfa.bank.model.CustomFileInfo;

@Controller
public class FilesController {

    @Autowired
    private CustomFileDao customFileDao;

    @Autowired
    private CustomFolderDao customFolderDao;

    @RequestMapping(value = "/showFiles", method = RequestMethod.GET)
    public String scanDisk(Model model) throws Exception {

        // 1- First scanDisk
        //scanDisk();

        // 2 - Display files
        List<CustomFileInfo> list = customFileDao.getFiles();

        model.addAttribute("listFileInfo", list);

        return "filesPage";
    }

    private void scanDisk() throws Exception {
        //List<File> allFiles = new ArrayList<File>();
        //List<File> allDirs = new ArrayList<File>();

        //String PATH_TO_SCAN = "E:\Music\mp3\Bandes Originales\Bo - American Beauty";

        /*
        File dirToScan = new File(PATH_TO_SCAN);
        File[] files = dirToScan.listFiles();

        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isDirectory()) {
                allDirs.add(file);
            } else {
                allFiles.add(file);
            }
        }
        */

    	String folderPath = addBackslash("G:\\XXX");
    	//String folderPath = addBackslash("E:\\Music");
    	//String folderPath = addBackslash("E:\\Music\\mp3");
    	//String folderPath = addBackslash("E:\\Music\\mp3\\Bandes Originales");
        //String folderPath = addBackslash("E:\\Music\\mp3\\Bandes Originales\\Bo - American Beauty");
        
        System.out.println(folderPath);
        
        //CustomFolderInfo folder = customFolderDao.findCustomFolderByFolderpath(folderPath);
        //scanFiles(PATH_TO_SCAN);
        
        scanFiles(folderPath);
        //read(allDirs);
        System.out.println("-------------------");
        //read(allFiles);
        System.out.println("-------------------");
        //System.out.println("Nombre de dossiers scannés: " + allDirs.size());
        //System.out.println("Nombre de fichiers scannés: " + allFiles.size());
    }

    private String addBackslash (String aString)   {
        return aString.replace("\\","\\\\");
    }
    private void scanFiles(String aPath) throws Exception {

    	
    	List<File> allFiles = new ArrayList<File>();
        List<File> allDirs = new ArrayList<File>();
    	// Create all folders first
    	 File dirToScan = new File(aPath);

    	 //File parentFile = dirToScan.getParentFile();
    	 File parentFile = dirToScan;
    	 HashMap<Integer, File> mapFolders = new HashMap<Integer, File>();
    	 LinkedHashMap<File, File> linkedFolderMap = new LinkedHashMap<>();
    	 int indexFolder = 1;
    	 while (parentFile != null) {
    		 
    		 if (parentFile.isDirectory())
    		 {
    			 allDirs.add(parentFile);
    		 }
    		 File newParentFile = parentFile.getParentFile();
    		 linkedFolderMap.put(parentFile, newParentFile);
    		 mapFolders.put(indexFolder, parentFile);
    		 indexFolder++;
    		 //parentFile = parentFile.getParentFile();
    		 parentFile = newParentFile;
    	 }
    	 
    	 Map<File, File> sortedMapByValue = sort(linkedFolderMap);
    	 
    	 int parentId = -1;
    	 for (Map.Entry<File, File> entry : sortedMapByValue.entrySet()) {
    		 File folder = entry.getKey();
    		 	
    		 String path = folder.getPath();
    		 
    		 CustomFolder customFolder = customFolderDao.findCustomFolderByFolderpath(path);
    		 if (null == customFolder)
    		 {
    			 int folderId = saveFolder(folder.getName(), parentId, path , 10, null, null);
    			 parentId = folderId;
    		 }
    		 else {
    			 parentId = customFolder.getId();
    		 }
    	 }
    	 
    	 /*
         Map<File, File> sortedMapByValue = linkedFolderMap.entrySet()
            	 .stream() 
            	 .filter (x -> x.getValue() != null)
            	 //.sorted(Map.Entry.comparingByValue())
            	 //.collect(Collectors.toMap(
            	 .collect(Collectors.toMap(
            			 //System.out.println(Map.Entry::getKey);
            	   Map.Entry::getKey, Map.Entry::getValue,		 
            	 (e1, e2) -> e1,
            	 LinkedHashMap::new
            	 ));
         */
        
        DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get(aPath));
        for (Path path : dirStream) {
            //System.out.println(path.getFileName());
            File file = path.toFile();
            String pathFile = path.getParent().toFile().getPath();
            
            int folderId = -1;
            CustomFolder customFolder = customFolderDao.findCustomFolderByFolderpath(pathFile);
            if (null == customFolder)
   		 	{
            	throw new Exception ("No Folder found for the file "+file.getPath());
   		 	}
            else {
            	folderId = customFolder.getId();
            }
            if (file.isDirectory()) {
                //allDirs.add(file);
                //File [] subFiles = file.listFiles();
                //System.out.println(file.getPath());
                // TODO : insert in DB

                scanFiles(file.getPath());
            } else {
                String filename = file.getName();
                int lastIndexExtension = filename.lastIndexOf(".");
                
                String extension = null;
                if (lastIndexExtension != -1) {
                	extension = filename.substring(lastIndexExtension+1);
                }
                
                String filePath = file.getParentFile().getPath();
                //int size = (int) file.length();
                long size = file.length();
                if (size < 0)
                {
                	System.out.println("kk");
                }
                Date creationDate = new Date();
                Date updateDate = null;

                CustomFile customFile = customFileDao.findCustomFileByFolderIdAndName(folderId, filename);
       		 	if (null == customFile)
       		 	{
       		 		saveFile(filename, folderId, extension, filePath, size, creationDate, updateDate);
       		 	}
                //allFiles.add(file);
            }
        }
    }

    private void saveFile(String filename, int folderId, String extension, String path, long size, Date creationDate, Date updateDate) {
        try {
            //Insert into myfiles (filename, extension, folderId, size, absolutePath, creationDate, updateDate) values ('test2', 'txt', 1, '20', 'C:', null, null);
            customFileDao.saveFile(filename, extension, folderId, size, path, creationDate, updateDate);
        } catch (
                CustomFileTransactionException e) {
            //model.addAttribute("errorMessage", "Error: " + e.getMessage());
        }
    }

    private int saveFolder(String folderName, int idParent, String folderPath, int size, Date creationDate, Date updateDate) {
    	int folderId = -1;
        try {
            //Insert into myfolders (folderame, idParent, size, creationDate, updateDate) values ('test2', 'txt', 1, '20', 'C:', null, null);
            folderId = customFolderDao.saveFolder(folderName, idParent, folderPath, size, creationDate, updateDate);
        } catch (
                CustomFileTransactionException e) {
            //model.addAttribute("errorMessage", "Error: " + e.getMessage());
        }
        return folderId;
    }
    
    private static HashMap<File, File> sort(HashMap<File, File> map) {
        List<?> linkedlist = new LinkedList(map.entrySet());
        Collections.sort(linkedlist, new Comparator() {
             public int compare(Object o1, Object o2) {
            	 if (((Map.Entry) (o1)).getValue() == null || 
            			 ((Map.Entry) (o2)).getValue() == null)
            	 {
            		 return -1;
            	 }
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                   .compareTo(((Map.Entry) (o2)).getValue());
             }
        });
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = linkedlist.iterator(); it.hasNext();) {
               Map.Entry entry = (Map.Entry) it.next();
               sortedHashMap.put(entry.getKey(), entry.getValue());
        } 
        return sortedHashMap;
   }

}