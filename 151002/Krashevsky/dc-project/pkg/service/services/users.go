package services

import (
	"dc-project/pkg/model"
	"dc-project/pkg/repository"
	"errors"
	"github.com/go-playground/validator/v10"
)

type UsersService struct {
	repo repository.User
}

func NewUsersService(repo repository.User) *UsersService {
	return &UsersService{repo: repo}
}

func (s *UsersService) CreateUser(user model.User) (model.User, error) {
	validate := validator.New()
	if err := validate.Struct(user); err != nil {
		return model.User{}, err
	}
	return s.repo.CreateUser(user)
}

func (s *UsersService) GetAllUsers() ([]model.User, error) {
	return s.repo.GetAllUsers()
}

func (s *UsersService) GetUserById(id int) (model.User, error) {
	return s.repo.GetUserById(id)
}

func (s *UsersService) DeleteUser(id int) error {
	num, err := s.repo.DeleteUser(id)
	if err == nil && num == 0 {
		return errors.New("comment with specified id not found")
	}
	return err
}

func (s *UsersService) UpdateUser(id int, user model.User) (model.User, error) {
	validate := validator.New()
	if err := validate.Struct(user); err != nil {
		return model.User{}, err
	}
	return s.repo.UpdateUser(id, user)
}
