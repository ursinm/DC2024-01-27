import { NestFactory } from "@nestjs/core";
import { AppModule } from "./app.module";

async function bootstrap() {
  const app = await NestFactory.create(AppModule, { abortOnError: false });
  app.setGlobalPrefix("api/v1.0");
  await app.listen(process.env.PORT as string, () =>
    console.log(`Server is running on the port ${process.env.PORT}`)
  );
}
bootstrap();
