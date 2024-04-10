using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace WebApplicationDC1.Migrations
{
    /// <inheritdoc />
    public partial class InitialCreate : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "tbl_creators",
                columns: table => new
                {
                    Id = table.Column<int>(type: "INTEGER", nullable: false)
                        .Annotation("Sqlite:Autoincrement", true),
                    Login = table.Column<string>(type: "TEXT", maxLength: 64, nullable: false),
                    Password = table.Column<string>(type: "TEXT", maxLength: 128, nullable: false),
                    FirstName = table.Column<string>(type: "TEXT", maxLength: 64, nullable: false),
                    LastName = table.Column<string>(type: "TEXT", maxLength: 64, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tbl_creators", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "tbl_stickers",
                columns: table => new
                {
                    Id = table.Column<int>(type: "INTEGER", nullable: false)
                        .Annotation("Sqlite:Autoincrement", true),
                    Name = table.Column<string>(type: "TEXT", maxLength: 32, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tbl_stickers", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "tbl_storys",
                columns: table => new
                {
                    Id = table.Column<int>(type: "INTEGER", nullable: false)
                        .Annotation("Sqlite:Autoincrement", true),
                    Title = table.Column<string>(type: "TEXT", maxLength: 64, nullable: false),
                    Content = table.Column<string>(type: "TEXT", maxLength: 2048, nullable: false),
                    Created = table.Column<DateTime>(type: "TEXT", nullable: false),
                    Modified = table.Column<DateTime>(type: "TEXT", nullable: false),
                    CreatorId = table.Column<int>(type: "INTEGER", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tbl_storys", x => x.Id);
                    table.ForeignKey(
                        name: "FK_tbl_storys_tbl_creators_CreatorId",
                        column: x => x.CreatorId,
                        principalTable: "tbl_creators",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "StickerStory",
                columns: table => new
                {
                    StickersId = table.Column<int>(type: "INTEGER", nullable: false),
                    StoriesId = table.Column<int>(type: "INTEGER", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_StickerStory", x => new { x.StickersId, x.StoriesId });
                    table.ForeignKey(
                        name: "FK_StickerStory_tbl_stickers_StickersId",
                        column: x => x.StickersId,
                        principalTable: "tbl_stickers",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_StickerStory_tbl_storys_StoriesId",
                        column: x => x.StoriesId,
                        principalTable: "tbl_storys",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "tbl_posts",
                columns: table => new
                {
                    Id = table.Column<int>(type: "INTEGER", nullable: false)
                        .Annotation("Sqlite:Autoincrement", true),
                    StoryId = table.Column<int>(type: "INTEGER", nullable: false),
                    Content = table.Column<string>(type: "TEXT", maxLength: 2048, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tbl_posts", x => x.Id);
                    table.ForeignKey(
                        name: "FK_tbl_posts_tbl_storys_StoryId",
                        column: x => x.StoryId,
                        principalTable: "tbl_storys",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_StickerStory_StoriesId",
                table: "StickerStory",
                column: "StoriesId");

            migrationBuilder.CreateIndex(
                name: "IX_tbl_creators_Login",
                table: "tbl_creators",
                column: "Login",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_tbl_posts_StoryId",
                table: "tbl_posts",
                column: "StoryId");

            migrationBuilder.CreateIndex(
                name: "IX_tbl_stickers_Name",
                table: "tbl_stickers",
                column: "Name",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_tbl_storys_CreatorId",
                table: "tbl_storys",
                column: "CreatorId");

            migrationBuilder.CreateIndex(
                name: "IX_tbl_storys_Title",
                table: "tbl_storys",
                column: "Title",
                unique: true);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "StickerStory");

            migrationBuilder.DropTable(
                name: "tbl_posts");

            migrationBuilder.DropTable(
                name: "tbl_stickers");

            migrationBuilder.DropTable(
                name: "tbl_storys");

            migrationBuilder.DropTable(
                name: "tbl_creators");
        }
    }
}
