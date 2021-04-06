package model

type Node struct {
	NodeId      string      `json:"id" form:"id"`
	Name        string      `json:"name" form:"name"`
	ObjectValue interface{} `json:"value" form:"value"`
}

type Edge struct {
	EdgeId           string `json:"id" form:"id"`
	EdgeRelationship string `json:"relationship" form:"relationship"`
	StartId          string `json:"source" form:"source"`
	EndId            string `json:"target" form:"target"`
}

type Complex struct {
	Node []Node `json:"node"`
	Edge []Edge `json:"edge"`
}
