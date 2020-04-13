package com.uuwatch.spider.manager.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;


/**
 * 文件处理辅助程序
 * 
 * @author caviler
 * @author ice
 */
public final class FileUtil
{
    /**
     * 备份视频文件
     * 
     * @param sFile
     *            带备份的文件
     * @param sBakFile
     *            备份文件备份地址
     * @return 返回备份结果(成功：true;失败:false)
     * @throws IOException
     */
    public static boolean bakVideoFile(final String sFile, final String sBakFile) throws IOException
    {
        if (null == sFile || null == sBakFile)
        {
            return false;
        }

        //拷贝文件
        copyFile(sFile, sBakFile);

        return true;
    }

    /**
     * 检查目录是否存在
     * 
     * @param sFile
     *            目录名（包含完整的全路径）
     * 
     * @return 返回目录存在状态(不存在不抛出异常)
     */
    public static boolean checkDirExists(final String sDir)
    {
        boolean tb = true; //目录存在标记(默认目录存在)

        final File oFile = new File(sDir);
        if (!oFile.exists())
        {
            tb = false;
        }
        return tb;
    }

    /**
     * 检查文件是否存在
     * 
     * @param sFile
     *            文件名（包含完整的全路径）
     * 
     * @return 返回文件存在状态(不存在会抛出异常)
     */
    public static boolean checkFile(final String sFile) throws FileNotFoundException
    {
        final boolean tb = true; // 所有文件是否都存在

        final File oFile = new File(sFile);
        if (!oFile.exists())
        {
            throw new FileNotFoundException(sFile + " 文件不存在！");
        }
        if (oFile.exists() && oFile.length() == 0)
        {
//            throw new CommonException(sFile + " 文件大小为:0 KB");
        }

        return tb;
    }

    /**
     * 检查文件是否存在
     * 
     * @param sFile
     *            文件名（包含完整的全路径）
     * 
     * @return 返回文件存在状态(不存在不抛出异常)
     */
    public static boolean checkFileExist(final String sFile)
    {
        boolean tb = true; //文件存在标识(默认文件存在)

        final File oFile = new File(sFile);
        if (!oFile.exists())
        {
            tb = false;
        }
        if (oFile.exists())
        {
            if (oFile.isDirectory())
            {
                tb = true;
            }
            else
            {
                if (oFile.length() == 0)
                {
                    tb = false;
                }
            }
        }
        return tb;
    }

    /**
     * 复制文件
     * 
     * @param sFile1
     *            源
     * @param sFile2
     *            目的
     * @throws IOException
     */
    public static void copyFile(final String sFile1, final String sFile2) throws IOException
    {
        final File oFile1 = new File(sFile1);
        if (oFile1.exists())
        {
            final String sPath = sFile2.substring(0, sFile2.lastIndexOf(File.separator));
            createFolder(sPath); // 确保目标目录存在

            final File oFile2 = new File(sFile2);
            final RandomAccessFile inData = new RandomAccessFile(oFile1, "r");
            final RandomAccessFile opData = new RandomAccessFile(oFile2, "rw");
            final FileChannel inChannel = inData.getChannel();
            final FileChannel opChannel = opData.getChannel();
            inChannel.transferTo(0, inChannel.size(), opChannel);
            //=========================上一行代码与下面的代码功能相同=========================
            //			final long size = inChannel.size();
            //			final MappedByteBuffer buf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, size);
            //			opChannel.write(buf);
            //=================================================================
            inChannel.close();
            inData.close();
            opChannel.close();
            opData.close();
        }
    }

    /**
     * 创建多级目录
     * 
     * @param sPath
     *            目录
     * @return 是否创建成功
     */
    public static boolean createFolder(final String sPath)
    {
        final File oPath = new File(sPath);
        return oPath.mkdirs();
    }

    /**
     * 创建多级目录
     * 
     * @param sFile
     *            目录
     * @return 是否创建成功
     */
    public static boolean createFolderByFile(final String sFile)
    {
        final int nPos = sFile.lastIndexOf(File.separator);
        if (-1 != nPos)
        {
            return createFolder(sFile.substring(0, nPos));
        }
        return false;
    }

    /**
     * 删除具体的文件
     * 
     * @param file
     */
    public static boolean deleteFile(final String file)
    {

        try
        {
            final File f = new File(file);
            if (f.isFile())
            {
                return f.delete();
            }
        }
        catch (final Throwable e)
        {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除目录及其子目录(包括空和非空目录)
     * 
     * @param oPath
     *            目录
     * @return true删除成功,false不成功
     */
    private static boolean deleteFolder(final File oPath)
    {
        final File[] dirs = oPath.listFiles();
        if (dirs != null)
        {
            for (final File oSubPath : dirs)
            {
                if (oSubPath.isDirectory())
                {
                    if (!deleteFolder(oSubPath))
                    {
                        return false;
                    }
                }
                else
                {
                    if (!oSubPath.delete())
                    {
                        return false;
                    }
                }
            }
        }
        return oPath.delete(); // 删除目录
    }

    /**
     * 删除目录及其子目录(包括空和非空目录)
     * 
     * @see #deleteFolder(String)
     * @param sPath
     *            目录
     */
    public static void deleteFolder(final String sPath)
    {
        final File oPath = new File(sPath);
        if (!oPath.exists() || !oPath.isDirectory())
        {
            return;
        }

        deleteFolder(oPath);
    }

    /**
     * 删除视频文件
     * 
     * @param oFile
     *            待删除的文件
     * @return 返回删除结果(成功：true;失败:false)
     */
    public static boolean deleteVideoFile(final File oFile)
    {
        if (null == oFile)
        {
            return false;
        }

        return oFile.delete();
    }

    /**
     * 规格化路径
     * 
     * @param sPath
     *            路径
     * @return 规格化后的路径
     */
    public static String formatPath(final String sPath)
    {
        if (StringUtils.isEmpty(sPath))
        {
            return sPath;
        }
        final String sTemp = sPath.replace('/', File.separatorChar);
        return sTemp.endsWith(File.separator) ? sTemp : (sTemp + File.separator);
    }

    public static long getFileSize(final String sFile)
    {
        return new File(sFile).length();
    }

    public static String getResourcePath(final String resource) throws FileNotFoundException
    {
        final File file = ResourceUtils.getFile("classpath:" + resource);
        return file.getAbsolutePath();
    }

    /**
     * 判断目标目录是否为空目录
     * 
     * @param path
     * @return
     */
    public static boolean isEmptyDirectory(final String path)
    {
        final File fso = new File(path);
        if (fso.exists())
        {
            final String[] files = fso.list();
            if (files.length > 0)
            {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断两个文件是否相同
     * 
     * @param oFile1
     *            文件对象
     * @param oFile2
     *            文件对象
     * @return true:文件相同,false:文件不同
     */
    public static boolean isSameFile(final File oFile1, final File oFile2)
    {
        return (null == oFile1 || null == oFile2) ? false : oFile1.compareTo(oFile2) == 0;
    }

    /**
     * 判断两个文件是否在同一目录下
     * 
     * @param sFile1
     *            文件对象
     * @param sFile2
     *            文件对象
     * @return true:在经同一目录下,false:不在同一目录下
     */
    public static boolean isSameFilePath(final File sFile1, final File sFile2)
    {
        return (null == sFile1 || null == sFile2) ? false : sFile1.getParent().equals(sFile2.getParent());
    }

    /**
     * 判断两文件后缀名是否相同
     * 
     * @param oFile1
     *            文件1
     * @param oFile2
     *            文件2
     * @return true:后缀相同,false:后缀不同
     */
    public static boolean isSamePostfix(final File oFile1, final File oFile2)
    {
        if (null == oFile1 || null == oFile2)
        {
            return false;
        }

        final String sPath1 = oFile1.getPath();
        final String sPath2 = oFile2.getPath();
        final String sPostfix1 = sPath1.substring(sPath1.lastIndexOf("."), sPath1.length());
        final String sPostfix2 = sPath2.substring(sPath2.lastIndexOf("."), sPath2.length());
        return sPostfix1.equals(sPostfix2);
    }

    /**
     * 移动文件
     * 
     * @param sFile1
     *            待移动的文件
     * @param sFile2
     *            待替换的文件
     * @param bReplace
     *            如果目标已存在是否覆盖
     * @throws IOException
     */
    public static boolean moveFile(final String sFile1, final String sFile2, final boolean bReplace) throws IOException
    {

        if ((sFile1 == null) || (sFile2 == null))
        {
            return false;
        }

        final File oFile1 = new File(sFile1);
        final File oFile2 = new File(sFile2);

        if (!oFile1.exists() || oFile1.isDirectory())
        {
            throw new IOException("文件" + sFile1 + "不存在或这是一个目录，导致文件移动失败！");
        }

        if (oFile2.exists())
        {
            // 如果目标已存在是否覆盖
            if (!bReplace)
            {
                return false;
            }

            if (!oFile2.delete())
            {
                return false;
            }
        }

        createFolder(oFile2.getParent()); // 确保目标目录存在;

        // 首先尝试通过改名来实现文件移动
        if (oFile1.renameTo(oFile2))
        {
            return true;
        }

        // 通过改名来实现文件移动失败，则改用文件复制方式
        copyFile(sFile1, sFile2);
        return oFile1.delete();
    }

    /**
     * 文件移动(将sFile1移动到sFile2)
     * 
     * @param sFile1
     *            原始文件
     * @param sFile2
     *            移动后的文件
     * @param sFileBak
     *            备份的文件
     * @param bBackup
     *            是否需要备份
     * @param bReplace
     *            是否允许覆盖
     * @param isMergeFile
     *            是否是文件合并操作(还有可能是文件移动操作),如果是文件合并操作则不允许使用重命名方式
     * @throws IOException
     */
    private static void moveFile(final String sFile1,
                                 final String sFile2,
                                 final String sFileBak,
                                 final boolean bBackup,
                                 final boolean bReplace,
                                 final List<String[]> moveLogs) throws IOException
    {
        final String[] moveLog = new String[2]; //此数据存放两个文件信息，第一个为原始文件，第二个

        if (checkFileExist(sFile2) && bBackup)
        {
            //1、备份文件 
            moveFile(sFile2, sFileBak, true);

            //2、记录备份操作
            moveLog[0] = sFile2;
            moveLog[1] = sFileBak;
            moveLogs.add(moveLog);
        }

        try
        {
            //3、将sFile1移动到sFile2(允许覆盖sFile2)
            moveFile(sFile1, sFile2, bReplace);
            //4、记录备份操作
            moveLog[0] = sFile1;
            moveLog[1] = sFile2;
            moveLogs.add(moveLog);
        }
        catch (final IOException e)
        {
            throw new IOException(e);
        }

    }

    /**
     * 文件移动(将sFile1移动到sFile2)
     * 
     * 说明:sFile1和sFile2两个数组中的文件类型必须一一对应，否则移动文件会失败
     * 
     * @param sFile1
     *            要保留的文件信息
     * @param sFile2
     *            待被替换的文件信息
     * @param sFileBak
     *            待备份的文件信息
     * @param bReplace
     *            替换时若发现备份目录下有相同名称的文件是否需要替换
     * @throws IOException
     */
    public static void moveFile(final String[] sFile1,
                                final String[] sFile2,
                                final String[] sFileBak,
                                final boolean bBackup,
                                final boolean bReplace) throws IOException
    {
        if (null != sFile1 && null != sFile2 && sFile1.length > 0 && sFile1.length == sFile2.length)
        {
            //检查文件数组中对应位置的文件后缀名是否相同
            for (int i = 0; i < sFile1.length; i++)
            {
                final File oFile1 = new File(sFile1[i]);
                final File oFile2 = new File(sFile2[i]);
                if (!isSamePostfix(oFile1, oFile2))
                {
                    throw new IOException("待移动的文件组中文件后缀名不相同，文件移动失败！");
                }
                if (isSameFile(oFile1, oFile2))
                {
                    throw new IOException("待移动的文件组中文件与目标目录文件相同(同一个文件不需要移动)，请确认代移动文件是否需要移动！");
                }
            }

            //检查原始文件是否存在，不存在会抛出相应的异常
            for (final String sFile : sFile1)
            {
                checkFile(sFile);
            }

            //文件移动历史记录集合，用于记录文件移动信息，当移动失败时根据记录进行文件回滚操作
            final List<String[]> moveLogs = new ArrayList<String[]>();

            try
            {
                for (int i = 0; i < sFile1.length; i++)
                {
                    moveFile(sFile1[i], sFile2[i], sFileBak[i], bBackup, bReplace, moveLogs);
                }
            }
            catch (final IOException e)
            {
                final int nSize = moveLogs.size();
                if (nSize > 0)
                {
                    for (int i = nSize - 1; i >= 0; i--)
                    {
                        final String[] sMoveLog = moveLogs.get(i);
                        try
                        {
                            moveFile(sMoveLog[1], sMoveLog[0], false);
                        }
                        catch (final IOException e1)
                        {
                            throw new IOException("文件移动失败且回滚文件时发生错误：" + e);
                        }
                    }
                    //回滚完成之后抛出异常，通知用户文件移动失败。
                    throw new IOException("文件移动失败，但已将移动的文件回滚，移动失败原因：" + e);
                }
                else
                {
                    throw new IOException("文件移动失败：" + e);
                }
            }
        }
    }

    public static StringBuilder read(final String sFile)
    {
        final File oFile1 = new File(sFile);

        if (oFile1.exists())
        {
            final StringBuilder sb = new StringBuilder();

            try
            {
                final Scanner sc = new Scanner(oFile1, "UTF-8");
                while (sc.hasNextLine())
                {
                    final String temp = sc.nextLine();
                    sb.append(temp + "\r\n");
                }

                sc.close();

                return sb;
            }

            catch (final FileNotFoundException e)
            {
                e.printStackTrace();
            }

        }

        return null;
    }

    /**
     * 写文件
     * 
     * @param file
     *            文件名称
     * @param content
     *            文件内容
     * @throws IOException
     *             抛出写文件异常
     */
    public static void writeFile(final String file, final byte[] content) throws IOException
    {
        final FileOutputStream fos = new FileOutputStream(file);

        fos.write(content);
        fos.flush();
        fos.close();
    }

    /**
     * 写文件
     * 
     * @param content
     *            文件内容
     * @param file
     *            文件名称
     * @param encoding
     *            编码(常用编码：UTF-8，StringDecoder.CHINESE_CHARSET)
     * @throws IOException
     *             抛出写文件异常
     */
    public static void writeFile(final String file, final String content, final String encoding) throws IOException
    {
        final OutputStreamWriter outfile = new OutputStreamWriter(new FileOutputStream(file), encoding); //指定文件格式 

        outfile.write(content);
        outfile.flush();
        outfile.close();
    }

    /**
     * 写文件
     * 
     * @param fileStr
     *            文件内容
     * @param sPath
     *            文件路径
     * @param sEncode
     *            编码(常用编码：UTF-8，StringDecoder.CHINESE_CHARSET)
     * @throws Exception
     *             抛出写文件异常
     */
    @Deprecated
    public static void writerFile(final String fileStr, final String sPath, final String sEncode) throws IOException
    {
        final OutputStreamWriter outfile = new OutputStreamWriter(new FileOutputStream(sPath), sEncode); //指定文件格式 

        outfile.write(fileStr);
        outfile.flush();
        outfile.close();
    }

    /**
     * 写文件
     * 
     * @param fileStr
     *            文件内容
     * @param sPath
     *            文件路径
     * @param sEncode
     *            编码(常用编码：UTF-8，StringDecoder.CHINESE_CHARSET)
     * @throws Exception
     *             抛出写文件异常
     */
    @Deprecated
    public static void writerFile(final String fileStr, final String sPath, final String sEncode, final boolean b)
            throws IOException
    {
        final OutputStreamWriter outfile = new OutputStreamWriter(new FileOutputStream(sPath, b), sEncode); //指定文件格式 

        outfile.write(fileStr);
        outfile.flush();
        outfile.close();
    }

    /**
     * 私有构造函数防止被构建
     */
    private FileUtil()
    {
    }

    //	public static boolean checkFileIsPicture(final String suffix)
    //	{
    //		final String[] ts = new String[]
    //		{ "JPG", "JPEG", "GIF", "PNG", "BMP" };
    //		for (final String s : ts)
    //		{
    //			if (suffix.toUpperCase().equals(s))
    //			{
    //				return true;
    //			}
    //		}
    //		return false;
    //	}
}
