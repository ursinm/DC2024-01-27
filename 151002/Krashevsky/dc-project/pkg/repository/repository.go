package repository

import (
	"dc-project/pkg/model"
	"dc-project/pkg/repository/postgresql"
	"github.com/jmoiron/sqlx"
)

type User interface {
	CreateUser(user model.User) (model.User, error)
	GetAllUsers() ([]model.User, error)
	GetUserById(id int) (model.User, error)
	DeleteUser(id int) (int, error)
	UpdateUser(id int, user model.User) (model.User, error)
}

type Story interface {
	CreateStory(story model.Story) (model.Story, error)
	GetAllStories() ([]model.Story, error)
	GetStoryById(id int) (model.Story, error)
	DeleteStory(id int) (int, error)
	UpdateStory(id int, user model.Story) (model.Story, error)
}

type Comment interface {
	CreateComment(comment model.Comment) (model.Comment, error)
	GetAllComments() ([]model.Comment, error)
	GetCommentById(id int) (model.Comment, error)
	DeleteComment(id int) (int, error)
	UpdateComment(id int, user model.Comment) (model.Comment, error)
}

type Sticker interface {
	CreateSticker(sticker model.Sticker) (model.Sticker, error)
	GetAllStickers() ([]model.Sticker, error)
	GetStickerById(id int) (model.Sticker, error)
	DeleteSticker(id int) (int, error)
	UpdateSticker(id int, user model.Sticker) (model.Sticker, error)
}

type Repository struct {
	User
	Story
	Comment
	Sticker
}

func NewRepository(db *sqlx.DB) *Repository {
	return &Repository{
		User:    postgresql.NewUsersPostgres(db),
		Story:   postgresql.NewStoriesPostgres(db),
		Comment: postgresql.NewCommentsPostgres(db),
		Sticker: postgresql.NewStickersPostgres(db),
	}
}
