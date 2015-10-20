package service;

public interface TaskGeneral
{
	String getDownFileName(String filecode);
	void updateFileStat(String filecode,int stat);
	int getFileStat(String filecode);
}