package db

import (
	"github.com/neo4j/neo4j-go-driver/neo4j"
	"log"
)

/*
	NodeCreate 公共API用于创建节点/关系
*/
func (dao *DaoStruct) NodeCreate(Cypher string, params map[string]interface{}) error {
	session, err := dao.neo4j.NewSession(neo4j.SessionConfig{AccessMode: neo4j.AccessModeWrite})
	if err != nil {
		return err
	}
	defer session.Close()

	_, err = session.WriteTransaction(func(tx neo4j.Transaction) (interface{}, error) {
		result, err := tx.Run(Cypher, params)
		if err != nil {
			log.Println("wirte to DB with error:", err)
			return nil, err
		}
		return result.Consume()
	})

	return err
}

/*
	NodeQuery is common API for Querying NODE in neo4j DB
   	the cypher string must use "n"(node) as the obj name
	example:
		  "MATCH (n) RETURN n, n.Desc as desString, n.name as objName LIMIT 100"
    neo4j.Node slice are return value.
*/
func (dao *DaoStruct) NodeQuery(Cypher string, params map[string]interface{}) ([]neo4j.Node, error) {
	var list []neo4j.Node
	session, err := dao.neo4j.NewSession(neo4j.SessionConfig{AccessMode: neo4j.AccessModeRead})
	if err != nil {
		return list, err
	}
	defer session.Close()
	_, err = session.ReadTransaction(func(tx neo4j.Transaction) (interface{}, error) {
		result, err := tx.Run(Cypher, params)
		if err != nil {
			return nil, err
		}

		for result.Next() {
			record := result.Record()
			if value, ok := record.Get("n"); ok {
				node := value.(neo4j.Node)
				list = append(list, node)
			}
		}
		if err = result.Err(); err != nil {
			return nil, err
		}

		return list, result.Err()
	})

	if err != nil {
		log.Println("Read error:", err)
	}
	return list, err
}

/*
	EdgeQuery is common API for Querying relationship in neo4j DB
   	the cypher string must use "r"(relationship) as the obj name
	example:
		  "MATCH (r) RETURN n, r.weight as weight LIMIT 100"
    neo4j.Relationship slice are return value.
*/
func (dao *DaoStruct) EdgeQuery(Cypher string, params map[string]interface{}) ([]neo4j.Relationship, error) {

	var list []neo4j.Relationship
	session, err := dao.neo4j.NewSession(neo4j.SessionConfig{AccessMode: neo4j.AccessModeWrite})
	if err != nil {
		return list, err
	}
	defer session.Close()
	_, err = session.ReadTransaction(func(tx neo4j.Transaction) (interface{}, error) {
		result, err := tx.Run(Cypher, params)
		if err != nil {
			log.Println("EdgeQuery Run failed: ", err)
			return nil, err
		}
		for result.Next() {
			record := result.Record()
			if value, ok := record.Get("r"); ok {
				relationship := value.(neo4j.Relationship)
				list = append(list, relationship)
			}
		}
		if err = result.Err(); err != nil {
			return nil, err
		}
		return list, result.Err()
	})

	if err != nil {
		log.Println("Read error:", err)
	}
	return list, err
}

/*
	ComplexQuery 用于查询边扩展
*/
func (dao *DaoStruct) ComplexQuery(Cypher string, params map[string]interface{}) ([]neo4j.Node, []neo4j.Relationship, error) {

	var elist []neo4j.Relationship
	var nlist []neo4j.Node
	session, err := dao.neo4j.NewSession(neo4j.SessionConfig{AccessMode: neo4j.AccessModeWrite})
	if err != nil {
		return nlist, elist, err
	}
	defer session.Close()
	_, err = session.ReadTransaction(func(tx neo4j.Transaction) (interface{}, error) {
		result, err := tx.Run(Cypher, params)
		if err != nil {
			log.Println("Complex Run failed: ", err)
			return nil, err
		}
		for result.Next() {
			record := result.Record()
			if value, ok := record.Get("r"); ok {
				relationship := value.(neo4j.Relationship)
				elist = append(elist, relationship)
			}

			if value, ok := record.Get("n2"); ok {
				node := value.(neo4j.Node)
				nlist = append(nlist, node)
			}
		}
		if err = result.Err(); err != nil {
			return nil, err
		}
		return nil, result.Err()
	})

	if err != nil {
		log.Println("Read error:", err)
	}
	return nlist, elist, err
}
