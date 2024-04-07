package services

import (
	"dc-project/pkg/model"
	"dc-project/pkg/repository"
	"errors"
	"github.com/go-playground/validator/v10"
)

type StickersService struct {
	repo repository.Sticker
}

func NewStickersService(repo repository.Sticker) *StickersService {
	return &StickersService{repo: repo}
}

func (s *StickersService) CreateSticker(sticker model.Sticker) (model.Sticker, error) {
	validate := validator.New()
	if err := validate.Struct(sticker); err != nil {
		return model.Sticker{}, err
	}
	return s.repo.CreateSticker(sticker)
}

func (s *StickersService) GetAllStickers() ([]model.Sticker, error) {
	return s.repo.GetAllStickers()
}

func (s *StickersService) GetStickerById(id int) (model.Sticker, error) {
	return s.repo.GetStickerById(id)
}

func (s *StickersService) DeleteSticker(id int) error {
	num, err := s.repo.DeleteSticker(id)
	if err == nil && num == 0 {
		return errors.New("comment with specified id not found")
	}
	return err
}

func (s *StickersService) UpdateSticker(id int, sticker model.Sticker) (model.Sticker, error) {
	validate := validator.New()
	if err := validate.Struct(sticker); err != nil {
		return model.Sticker{}, err
	}
	return s.repo.UpdateSticker(id, sticker)
}
