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
			CreateMap<Creator, CreatorResponseDto>();
			CreateMap<CreatorRequestDto, Creator>();

			CreateMap<Issue, IssueResponseDto>();
			CreateMap<IssueRequestDto, Issue>();

			CreateMap<Label, LabelResponseDto>();
			CreateMap<LabelRequestDto, Label>();

			CreateMap<Post, PostResponseDto>();
			CreateMap<PostRequestDto, Post>();
		}
	}
}
