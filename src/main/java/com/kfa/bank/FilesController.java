package com.kfa.bank;

import com.kfa.bank.dao.CustomFileDao;
import com.kfa.bank.dao.CustomFolderDao;
import com.kfa.bank.entity.CustomFolder;
import com.kfa.bank.exception.CustomFileTransactionException;
import com.kfa.bank.model.CustomFileInfo;
import com.kfa.bank.model.CustomFolderInfo;
import org.hibernate.sql.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class FilesController {

    @Autowired
    private CustomFileDao customFileDao;

    @Autowired
    private CustomFolderDao customFolderDao;

    @RequestMapping(value = "/showFiles", method = RequestMethod.GET)
    public String scanDisk(Model model) throws IOException {

        // 1- First scanDisk
        scanDisk();

        // 2 - Display files
        List<CustomFileInfo> list = customFileDao.getFiles();

        model.addAttribute("listFileInfo", list);

        return "filesPage";
    }

    private void scanDisk() throws IOException {
        //List<File> allFiles = new ArrayList<File>();
        //List<File> allDirs = new ArrayList<File>();

        String PATH_TO_SCAN = "E:\\Music";

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

        String folderPath = addBackslash("E:\\Music\\mp3\\Bandes Originales");
        //CustomFolderInfo folder = customFolderDao.findCustomFolderByFolderpath(folderPath);
        //scanFiles(PATH_TO_SCAN);
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
    private void scanFiles(String aPath) throws IOException {

        // insert root folder in base
        File rootFolder = new File(aPath);
        //String rootFolderName = rootFolder.getParentFile().getName();
        saveFolder(rootFolder.getName(), -1, rootFolder.getPath() , 10, null, null);
        DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get(aPath));
        for (Path path : dirStream) {
            //System.out.println(path.getFileName());
            File file = path.toFile();
            int folderId = 1;
            if (file.isDirectory()) {
                //allDirs.add(file);
                //File [] subFiles = file.listFiles();
                //System.out.println(file.getPath());
                // TODO : insert in DB

                scanFiles(file.getPath());
            } else {
                String filename = file.getName();
                String extension = ".txt";
                String filePath = file.getParentFile().getPath();
                int size = (int) file.length();
                Date creationDate = new Date();
                Date updateDate = null;

                saveFile(filename, folderId, extension, filePath, size, creationDate, updateDate);
                //allFiles.add(file);
            }
        }
    }

    private void saveFile(String filename, int folderId, String extension, String path, int size, Date creationDate, Date updateDate) {
        try {
            //Insert into myfiles (filename, extension, folderId, size, absolutePath, creationDate, updateDate) values ('test2', 'txt', 1, '20', 'C:', null, null);
            customFileDao.saveFile(filename, extension, folderId, size, path, creationDate, updateDate);
        } catch (
                CustomFileTransactionException e) {
            //model.addAttribute("errorMessage", "Error: " + e.getMessage());
        }
    }

    private void saveFolder(String folderName, int idParent, String folderPath, int size, Date creationDate, Date updateDate) {
        try {
            //Insert into myfolders (folderame, idParent, size, creationDate, updateDate) values ('test2', 'txt', 1, '20', 'C:', null, null);
            customFolderDao.saveFolder(folderName, idParent, folderPath, size, creationDate, updateDate);
        } catch (
                CustomFileTransactionException e) {
            //model.addAttribute("errorMessage", "Error: " + e.getMessage());
        }
    }

}