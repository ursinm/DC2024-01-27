using System.ComponentModel.DataAnnotations;
using lab_1.Domain;
using lab_1.Dtos.RequestDtos;
using lab_1.Dtos.RequestDtos.RequestConverters;
using lab_1.Dtos.ResponseDtos;
using lab_1.Dtos.ResponseDtos.ResponseConverters;
using lab_1.Repositories;

namespace lab_1.Services
{
    public class AuthorService : BaseService<AuthorRequestDto, AuthorResponseDto>
    {

        Repository<Author> authors;
        AuthorRequestConverter authorRequest;
        AuthorResponseConverter authorResponse;
        ListAuthorResponseConverter converter;
        public AuthorService() 
        {
            authors = new Repository<Author>();
            authorRequest = new AuthorRequestConverter();
            authorResponse = new AuthorResponseConverter();
            converter = new ListAuthorResponseConverter();
        }
        public AuthorResponseDto? Create(AuthorRequestDto dto)
        {
           authors.AddValue(authorRequest.FromDto(dto, authors.NextId));
           return authorResponse.ToDto(authors.FindById(authors.NextId-1));
        }

        public bool Delete(long id)
        {
            return authors.DeleteValue(id);
        }

        public AuthorResponseDto? Read(long id)=>authorResponse.ToDto(authors.FindById(id));
        

        public AuthorResponseDto? Update(AuthorRequestDto dto)
        {
            ICollection<ValidationResult> results = new List<ValidationResult>();
            var test = authorRequest.FromDto(dto, dto.id);
            if (Validator.TryValidateObject(test, new ValidationContext(test), results, validateAllProperties: true))
            {
                authors.UpdateValue(test, dto.id);
                return authorResponse.ToDto(authors.FindById(dto.id));
            }
            return null;
            
        }

        public List<AuthorResponseDto?> GetAll() => converter.AuthorsResponse(authors.GetAuthors()).ToList();
    }
}
