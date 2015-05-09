package query;

import global.Minibase;
import global.SearchKey;
import heap.HeapFile;
import index.HashIndex;
import parser.AST_CreateIndex;
import relop.FileScan;
import relop.Schema;
import relop.Tuple;

 

/**
 * Execution plan for creating indexes.
 */
class CreateIndex implements Plan {

	  /** Name of the Index. */
	  protected String indexName;
	  /** Name of the table to create. */
	  protected String tableName;
	  /** Name of the attribute to create. */
	  protected String columnName;
	  /** Schema of the table to create. */
      protected Schema schema;
	  
  /**
   * Optimizes the plan, given the parsed query.
   * 
   * @throws QueryException if index already exists or table/column invalid
   */
  public CreateIndex(AST_CreateIndex tree) throws QueryException {
         indexName = tree.getFileName();
         tableName = tree.getIxTable();
         columnName = tree.getIxColumn();
         
         QueryCheck.fileNotExists(tableName);
       try{
            QueryCheck.indexExists(indexName);
            throw new QueryException("index '" + indexName + "' already exists");
       }catch(QueryException e){
           
       }
       // get and validate the requested schema
          schema = Minibase.SystemCatalog.getSchema(tableName);
          QueryCheck.columnExists(schema, columnName);
        
        
    
  } // public CreateIndex(AST_CreateIndex tree) throws QueryException

  /**
   * Executes the plan and prints applicable output.
   */
  public void execute() {
    HashIndex colHashIndex = new HashIndex(tableName);
    FileScan tableScan = new FileScan(schema, new HeapFile(tableName));
    
    while(tableScan.hasNext()){
        Tuple tuple = tableScan.getNext();
        colHashIndex.insertEntry(new SearchKey(tuple.getField(columnName) ), tableScan.getLastRID()); 
    }
    
    tableScan.close();
    
     Minibase.SystemCatalog.createIndex(indexName, tableName,columnName);
    // print the output message
    System.out.println("Index created for attribute '" +columnName +"' in table '"+tableName+"'");

  } // public void execute()

} // class CreateIndex implements Plan
