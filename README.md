# zlib-tool-pes2018
Tool that compress and decompress PES 2018 files. 

If header ```WESYS``` is present in your file, maybe it can descompressed by this tool.

### Usage
```
C:\> java -jar zlib-tool-pes2018.jar [-option] [file_path]

 [-d] [file_path] -> descompress file
 [-c] [file_path] -> compress file

 [-db] [file_path] -> descompress file reading sizes in big endian format
 [-cb] [file_path] -> compress file and save sizes in big endian format
 [-cdds] [file_path] -> compress file and save dds default header (PS3)
```
#### Example
```
C:\> java -jar zlib-tool-pes2018.jar -d "C:\PES2018\Player.bin"
```
