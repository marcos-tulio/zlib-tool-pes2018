# zlib-tool-pes2018
Tool that compress and decompress PES 2018 files. 

If header ```WESYS``` is present in your file, maybe it can descompressed by this tool.

### Usage
```
C:\> java -jar zlib-tool-pes2018.jar [-option] [file_path]

 [-d] [file_path] -> decompress file
 [-c] [file_path] -> compress file

 [-db] [file_path] -> decompress file reading sizes in big endian format
 [-cb] [file_path] -> compress file and save sizes in big endian format
```
#### Example
```
C:\> java -jar zlib-tool-pes2018.jar -d "C:\PES2018\Player.bin"
```
