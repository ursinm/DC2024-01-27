using System;
using Microsoft.EntityFrameworkCore.Migrations;
using Npgsql.EntityFrameworkCore.PostgreSQL.Metadata;

#nullable disable

namespace RV.Migrations
{
    /// <inheritdoc />
    public partial class Initial : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "tbl_Sticker",
                columns: table => new
                {
                    id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    name = table.Column<string>(type: "character varying(32)", maxLength: 32, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tbl_Sticker", x => x.id);
                    table.CheckConstraint("len(name) < 2", "LENGTH(\"name\") > 2");
                });

            migrationBuilder.CreateTable(
                name: "tbl_User",
                columns: table => new
                {
                    id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    login = table.Column<string>(type: "character varying(64)", maxLength: 64, nullable: false),
                    password = table.Column<string>(type: "character varying(128)", maxLength: 128, nullable: false),
                    firstName = table.Column<string>(type: "character varying(64)", maxLength: 64, nullable: false),
                    lastName = table.Column<string>(type: "character varying(64)", maxLength: 64, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tbl_User", x => x.id);
                    table.CheckConstraint("len(firstName) < 2", "LENGTH(\"firstName\") > 2");
                    table.CheckConstraint("len(lastName) < 2", "LENGTH(\"lastName\") > 2");
                    table.CheckConstraint("len(login) < 2", "LENGTH(\"login\") > 2");
                    table.CheckConstraint("len(password) < 8", "LENGTH(\"password\") > 8");
                });

            migrationBuilder.CreateTable(
                name: "tbl_News",
                columns: table => new
                {
                    id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    UserId = table.Column<int>(type: "integer", nullable: false),
                    title = table.Column<string>(type: "character varying(64)", maxLength: 64, nullable: false),
                    content = table.Column<string>(type: "character varying(2048)", maxLength: 2048, nullable: false),
                    created = table.Column<DateTime>(type: "timestamp with time zone", nullable: false),
                    modified = table.Column<DateTime>(type: "timestamp with time zone", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tbl_News", x => x.id);
                    table.CheckConstraint("len(content) < 4", "LENGTH(\"content\") > 4");
                    table.CheckConstraint("len(title) < 2", "LENGTH(\"title\") > 2");
                    table.ForeignKey(
                        name: "FK_tbl_News_tbl_User_UserId",
                        column: x => x.UserId,
                        principalTable: "tbl_User",
                        principalColumn: "id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "NewsSticker",
                columns: table => new
                {
                    id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    NewsId = table.Column<int>(type: "integer", nullable: false),
                    StickerId = table.Column<int>(type: "integer", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_NewsSticker", x => x.id);
                    table.ForeignKey(
                        name: "FK_NewsSticker_tbl_News_NewsId",
                        column: x => x.NewsId,
                        principalTable: "tbl_News",
                        principalColumn: "id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_NewsSticker_tbl_Sticker_StickerId",
                        column: x => x.StickerId,
                        principalTable: "tbl_Sticker",
                        principalColumn: "id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "tbl_Note",
                columns: table => new
                {
                    id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    NewsId = table.Column<int>(type: "integer", nullable: false),
                    content = table.Column<string>(type: "character varying(2048)", maxLength: 2048, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tbl_Note", x => x.id);
                    table.CheckConstraint("len(content) < 2", "LENGTH(\"content\") > 2");
                    table.ForeignKey(
                        name: "FK_tbl_Note_tbl_News_NewsId",
                        column: x => x.NewsId,
                        principalTable: "tbl_News",
                        principalColumn: "id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_NewsSticker_NewsId",
                table: "NewsSticker",
                column: "NewsId");

            migrationBuilder.CreateIndex(
                name: "IX_NewsSticker_StickerId",
                table: "NewsSticker",
                column: "StickerId");

            migrationBuilder.CreateIndex(
                name: "IX_tbl_News_UserId",
                table: "tbl_News",
                column: "UserId");

            migrationBuilder.CreateIndex(
                name: "IX_tbl_Note_NewsId",
                table: "tbl_Note",
                column: "NewsId");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "NewsSticker");

            migrationBuilder.DropTable(
                name: "tbl_Note");

            migrationBuilder.DropTable(
                name: "tbl_Sticker");

            migrationBuilder.DropTable(
                name: "tbl_News");

            migrationBuilder.DropTable(
                name: "tbl_User");
        }
    }
}
