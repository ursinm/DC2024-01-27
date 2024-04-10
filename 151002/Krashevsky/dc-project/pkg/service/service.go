package service

import (
	"dc-project/pkg/model"
	"dc-project/pkg/repository"
	"dc-project/pkg/service/services"
)

type User interface {
	CreateUser(user model.User) (model.User, error)
	GetAllUsers() ([]model.User, error)
	GetUserById(id int) (model.User, error)
	DeleteUser(id int) error
	UpdateUser(id int, user model.User) (model.User, error)
}

type Story interface {
	CreateStory(story model.Story) (model.Story, error)
	GetAllStories() ([]model.Story, error)
	GetStoryById(id int) (model.Story, error)
	DeleteStory(id int) error
	UpdateStory(id int, story model.Story) (model.Story, error)
}

type Comment interface {
	CreateComment(comment model.Comment) (model.Comment, error)
	GetAllComments() ([]model.Comment, error)
	GetCommentById(id int) (model.Comment, error)
	DeleteComment(id int) error
	UpdateComment(id int, comment model.Comment) (model.Comment, error)
}

type Sticker interface {
	CreateSticker(sticker model.Sticker) (model.Sticker, error)
	GetAllStickers() ([]model.Sticker, error)
	GetStickerById(id int) (model.Sticker, error)
	DeleteSticker(id int) error
	UpdateSticker(id int, sticker model.Sticker) (model.Sticker, error)
}

type Service struct {
	User
	Story
	Comment
	Sticker
}

func NewService(repository *repository.Repository) *Service {
	return &Service{
		User:    services.NewUsersService(repository.User),
		Story:   services.NewStoriesService(repository.Story),
		Comment: services.NewCommentsService(repository.Comment),
		Sticker: services.NewStickersService(repository.Sticker),
	}
}
