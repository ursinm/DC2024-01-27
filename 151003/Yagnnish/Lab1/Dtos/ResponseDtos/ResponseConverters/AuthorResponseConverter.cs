using lab_1.Domain;

namespace lab_1.Dtos.ResponseDtos.ResponseConverters
{
    public class AuthorResponseConverter:BaseResponse<AuthorResponseDto,Author>
    {
        public AuthorResponseDto? ToDto(Author author) 
        {
            AuthorResponseDto? res = new AuthorResponseDto();
            res.firstname = author.Firstname;
            res.login = author.Login;
            res.lastname = author.Lastname;
            res.id = author.Id;
            res.password = author.Password; 
            return res;
        }
    }
}
