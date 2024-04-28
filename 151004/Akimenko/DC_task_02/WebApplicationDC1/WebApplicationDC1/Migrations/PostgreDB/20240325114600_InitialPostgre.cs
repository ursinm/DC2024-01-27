using System;
using Microsoft.EntityFrameworkCore.Migrations;
using Npgsql.EntityFrameworkCore.PostgreSQL.Metadata;

#nullable disable

namespace WebApplicationDC2.Migrations.PostgreDB
{
    /// <inheritdoc />
    public partial class InitialPostgre : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.EnsureSchema(
                name: "public");

            migrationBuilder.CreateTable(
                name: "tbl_creators",
                schema: "public",
                columns: table => new
                {
                    Id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    Login = table.Column<string>(type: "character varying(64)", maxLength: 64, nullable: false),
                    Password = table.Column<string>(type: "character varying(128)", maxLength: 128, nullable: false),
                    FirstName = table.Column<string>(type: "character varying(64)", maxLength: 64, nullable: false),
                    LastName = table.Column<string>(type: "character varying(64)", maxLength: 64, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tbl_creators", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "tbl_stickers",
                schema: "public",
                columns: table => new
                {
                    Id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    Name = table.Column<string>(type: "character varying(32)", maxLength: 32, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tbl_stickers", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "tbl_storys",
                schema: "public",
                columns: table => new
                {
                    Id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    Title = table.Column<string>(type: "character varying(64)", maxLength: 64, nullable: false),
                    Content = table.Column<string>(type: "character varying(2048)", maxLength: 2048, nullable: false),
                    Created = table.Column<DateTime>(type: "timestamp with time zone", nullable: false),
                    Modified = table.Column<DateTime>(type: "timestamp with time zone", nullable: false),
                    CreatorId = table.Column<int>(type: "integer", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tbl_storys", x => x.Id);
                    table.ForeignKey(
                        name: "FK_tbl_storys_tbl_creators_CreatorId",
                        column: x => x.CreatorId,
                        principalSchema: "public",
                        principalTable: "tbl_creators",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "StickerStory",
                schema: "public",
                columns: table => new
                {
                    StickersId = table.Column<int>(type: "integer", nullable: false),
                    StoriesId = table.Column<int>(type: "integer", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_StickerStory", x => new { x.StickersId, x.StoriesId });
                    table.ForeignKey(
                        name: "FK_StickerStory_tbl_stickers_StickersId",
                        column: x => x.StickersId,
                        principalSchema: "public",
                        principalTable: "tbl_stickers",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_StickerStory_tbl_storys_StoriesId",
                        column: x => x.StoriesId,
                        principalSchema: "public",
                        principalTable: "tbl_storys",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "tbl_posts",
                schema: "public",
                columns: table => new
                {
                    Id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    StoryId = table.Column<int>(type: "integer", nullable: false),
                    Content = table.Column<string>(type: "character varying(2048)", maxLength: 2048, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tbl_posts", x => x.Id);
                    table.ForeignKey(
                        name: "FK_tbl_posts_tbl_storys_StoryId",
                        column: x => x.StoryId,
                        principalSchema: "public",
                        principalTable: "tbl_storys",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_StickerStory_StoriesId",
                schema: "public",
                table: "StickerStory",
                column: "StoriesId");

            migrationBuilder.CreateIndex(
                name: "IX_tbl_creators_Login",
                schema: "public",
                table: "tbl_creators",
                column: "Login",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_tbl_posts_StoryId",
                schema: "public",
                table: "tbl_posts",
                column: "StoryId");

            migrationBuilder.CreateIndex(
                name: "IX_tbl_stickers_Name",
                schema: "public",
                table: "tbl_stickers",
                column: "Name",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_tbl_storys_CreatorId",
                schema: "public",
                table: "tbl_storys",
                column: "CreatorId");

            migrationBuilder.CreateIndex(
                name: "IX_tbl_storys_Title",
                schema: "public",
                table: "tbl_storys",
                column: "Title",
                unique: true);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "StickerStory",
                schema: "public");

            migrationBuilder.DropTable(
                name: "tbl_posts",
                schema: "public");

            migrationBuilder.DropTable(
                name: "tbl_stickers",
                schema: "public");

            migrationBuilder.DropTable(
                name: "tbl_storys",
                schema: "public");

            migrationBuilder.DropTable(
                name: "tbl_creators",
                schema: "public");
        }
    }
}
