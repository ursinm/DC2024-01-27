package services

import (
	"dc-project/pkg/model"
	"dc-project/pkg/repository"
	"errors"
	"github.com/go-playground/validator/v10"
	"time"
)

type StoriesService struct {
	repo repository.Story
}

func NewStoriesService(repo repository.Story) *StoriesService {
	return &StoriesService{repo: repo}
}

func (s *StoriesService) CreateStory(story model.Story) (model.Story, error) {
	validate := validator.New()
	if err := validate.Struct(story); err != nil {
		return model.Story{}, err
	}
	story.Created = time.Now()
	story.Modified = time.Now()
	return s.repo.CreateStory(story)
}

func (s *StoriesService) GetAllStories() ([]model.Story, error) {
	return s.repo.GetAllStories()
}

func (s *StoriesService) GetStoryById(id int) (model.Story, error) {
	return s.repo.GetStoryById(id)
}

func (s *StoriesService) DeleteStory(id int) error {
	num, err := s.repo.DeleteStory(id)
	if err == nil && num == 0 {
		return errors.New("comment with specified id not found")
	}
	return err
}

func (s *StoriesService) UpdateStory(id int, story model.Story) (model.Story, error) {
	validate := validator.New()
	if err := validate.Struct(story); err != nil {
		return model.Story{}, err
	}
	story.Modified = time.Now()
	return s.repo.UpdateStory(id, story)
}
