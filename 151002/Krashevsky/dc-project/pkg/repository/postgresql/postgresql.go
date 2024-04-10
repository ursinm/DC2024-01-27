package postgresql

import (
	"github.com/jmoiron/sqlx"
)

const (
	usersTable           = "tbl_users"
	storiesTable         = "tbl_stories"
	commentsTable        = "tbl_comments"
	stickersTable        = "tbl_stickers"
	storiesStickersTable = "tbl_stories_stickers"
)

type Config struct {
	Host     string
	Port     string
	Username string
	Password string
	DBName   string
	SSLMode  string
}

func New(source string) (*sqlx.DB, error) {
	db, err := sqlx.Open("postgres", source)
	if err != nil {
		return nil, err
	}

	err = db.Ping()
	if err != nil {
		return nil, err
	}

	return db, nil
}
