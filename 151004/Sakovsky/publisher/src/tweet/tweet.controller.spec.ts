import { Test, TestingModule } from '@nestjs/testing';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Tweet } from 'src/entities/Tweet';
import { TweetController } from 'src/tweet/tweet.controller';
import { TweetService } from 'src/tweet/tweet.service';

describe('TweetController', () => {
  let controller: TweetController;
  let service: TweetService;

  beforeEach(async () => {
    const moduleRef = await Test.createTestingModule({
        imports: [TypeOrmModule.forFeature([Tweet])],
        controllers: [TweetController],
        providers: [TweetService],
      }).compile();

    service = moduleRef.get<TweetService>(TweetService);
    controller = moduleRef.get<TweetController>(TweetController);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });
});
