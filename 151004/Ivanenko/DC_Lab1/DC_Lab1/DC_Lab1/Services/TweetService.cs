using AutoMapper;
using DC_Lab1.DTO;
using DC_Lab1.DTO.Interface;
using DC_Lab1.Models;
using DC_Lab1.Services.Interfaces;
using Humanizer;
using Microsoft.EntityFrameworkCore;
using System.Globalization;

namespace DC_Lab1.Services
{
    public class TweetService(IMapper _mapper, LabDbContext dbContext) : ITweetService
    {
        public async Task<IResponseTo> CreateEnt(IRequestTo Dto)
        {
            var TweetDto = (TweetRequestTo)Dto;

            try
            {
                if (Validate(TweetDto))
                {
                    var Tweet = _mapper.Map<Tweet>(TweetDto);
                    string time = DateTime.UtcNow.ToString("o", CultureInfo.InvariantCulture);
                    Tweet.Created = time;
                    Tweet.Modified = time;
                    dbContext.Tweets.Add(Tweet);
                    await dbContext.SaveChangesAsync();
                    var response = _mapper.Map<TweetResponseTo>(Tweet);
                    return response;

                }
                else
                {
                    throw new ArgumentException();
                }
            }
            catch
            {
                throw new ArgumentException();
            }

        }

        public async Task DeleteEnt(int id)
        {
            try
            {
                var Tweet = await dbContext.Tweets.FindAsync(id);
                dbContext.Tweets.Remove(Tweet!);
                await dbContext.SaveChangesAsync();
                return;
            }
            catch
            {
                throw new ArgumentException();
            }
        }

        public async Task<IResponseTo> GetEntById(int id)
        {
            try
            {
                return _mapper.Map<TweetResponseTo>(await dbContext.Tweets.FindAsync(id));

            }
            catch
            {
                throw new ArgumentException();
            }
        }

        public IEnumerable<IResponseTo> GetAllEnt()
        {
            try
            {
                return dbContext.Tweets.Select(_mapper.Map<TweetResponseTo>);

            }
            catch
            {
                throw new ArgumentException();
            }
        }

        public async Task<IResponseTo> UpdateEnt(IRequestTo Dto)
        {
            var TweetDto = (TweetRequestTo)Dto;
            try
            {
                if (Validate(TweetDto))
                {
                    var newTweet = _mapper.Map<Tweet>(TweetDto);
                    string time = DateTime.UtcNow.ToString("o", CultureInfo.InvariantCulture);
                    newTweet.Modified = time;
                    dbContext.Tweets.Update(newTweet);
                    await dbContext.SaveChangesAsync();
                    var Tweet = _mapper.Map<TweetResponseTo>(await dbContext.Tweets.FindAsync(newTweet.Id));
                    return Tweet;
                }
                else
                {
                    throw new ArgumentException();

                }
            }
            catch
            {

                throw new ArgumentException();
            }
        }

        public bool Validate(TweetRequestTo TweetDto)
        {
            if (TweetDto?.Title?.Length < 2 || TweetDto?.Title?.Length > 64)
                return false;
            if (TweetDto?.Content?.Length < 8 && TweetDto?.Content?.Length > 2048)
                return false;
            
            return true;
        }
    }
}
