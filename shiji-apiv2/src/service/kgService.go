package service

import (
	"apiv2/src/model"
	"strconv"
)

func (s *Service) GetNodeList(name string, limit int) (node []model.Node, err error) {
	data, err := s.dao.NodeQuery("match(n) where n.name=$name return n limit $limit",
		map[string]interface{}{"limit": limit, "name": name})

	if err != nil {
		return
	}

	for _, v := range data {
		node = append(node, model.Node{
			NodeId:      strconv.FormatInt(v.Id(), 10),
			Name:        v.Props()["name"].(string),
			ObjectValue: v.Props()["value"],
		})
	}

	return
}

func (s *Service) GetRelationshipList(query string, limit int) (edge []model.Edge, err error) {
	data, err := s.dao.EdgeQuery("match()-[r]-() where n1.name=$name return r limit $limit",
		map[string]interface{}{
			"limit": limit,
			"name":  query,
		})
	if err != nil {
		return
	}

	for _, v := range data {
		edge = append(edge, model.Edge{
			EdgeId:           strconv.FormatInt(v.Id(), 10),
			EdgeRelationship: v.Type(),
			StartId:          strconv.FormatInt(v.StartId(), 10),
			EndId:            strconv.FormatInt(v.EndId(), 10),
		})
	}
	return
}

func (s Service) ExpandNode(id int, limit int) (complex model.Complex, err error) {
	node, edge, err := s.dao.ComplexQuery("match(n1)-[r]-(n2) where id(n1)=$id return r, n2 limit $limit",
		map[string]interface{}{
			"id":    id,
			"limit": limit,
		})
	for _, v := range node {
		complex.Node = append(complex.Node, model.Node{
			NodeId:      strconv.FormatInt(v.Id(), 10),
			Name:        v.Props()["name"].(string),
			ObjectValue: v.Props()["value"],
		})
	}

	for _, v := range edge {
		complex.Edge = append(complex.Edge, model.Edge{
			EdgeId:           strconv.FormatInt(v.Id(), 10),
			EdgeRelationship: v.Type(),
			StartId:          strconv.FormatInt(v.StartId(), 10),
			EndId:            strconv.FormatInt(v.EndId(), 10),
		})
	}
	return
}

func (s *Service) GetAll() (complex []model.Complex, err error) {
	//node, edge, err := s.dao.ComplexQuery("match()-[r]-[n2] return r, n2", map[string]interface{}{})

	return
}
