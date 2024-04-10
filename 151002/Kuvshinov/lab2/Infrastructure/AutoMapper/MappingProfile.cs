using AutoMapper;
using DC.DTO.RequestDTO;
using DC.DTO.ResponseDTO;
using DC.Models;

namespace DC.Infrastructure.AutoMapper
{
	public class MappingProfile : Profile
	{
		public MappingProfile()
		{
			CreateMap<Editor, EditorResponseDto>();
			CreateMap<EditorRequestDto, Editor>();

			CreateMap<Story, StoryResponseDto>();
			CreateMap<StoryRequestDto, Story>();

			CreateMap<Label, LabelResponseDto>();
			CreateMap<LabelRequestDto, Label>();

			CreateMap<Note, NoteResponseDto>();
			CreateMap<NoteRequestDto, Note>();
		}
	}
}
