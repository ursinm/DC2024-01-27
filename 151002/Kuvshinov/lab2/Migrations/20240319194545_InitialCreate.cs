using System;
using Microsoft.EntityFrameworkCore.Migrations;
using Npgsql.EntityFrameworkCore.PostgreSQL.Metadata;

#nullable disable

namespace DC.Migrations
{
    /// <inheritdoc />
    public partial class InitialCreate : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "tblEditor",
                columns: table => new
                {
                    Id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    Login = table.Column<string>(type: "character varying(64)", maxLength: 64, nullable: false),
                    Password = table.Column<string>(type: "character varying(128)", maxLength: 128, nullable: false),
                    Firstname = table.Column<string>(type: "character varying(64)", maxLength: 64, nullable: false),
                    Lastname = table.Column<string>(type: "character varying(64)", maxLength: 64, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tblEditor", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "tblLabel",
                columns: table => new
                {
                    Id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    Name = table.Column<string>(type: "character varying(32)", maxLength: 32, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tblLabel", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "tblStory",
                columns: table => new
                {
                    Id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    EditorId = table.Column<long>(type: "bigint", nullable: false),
                    Title = table.Column<string>(type: "character varying(64)", maxLength: 64, nullable: false),
                    Content = table.Column<string>(type: "character varying(2048)", maxLength: 2048, nullable: false),
                    Created = table.Column<DateTime>(type: "timestamp with time zone", nullable: false),
                    Modified = table.Column<DateTime>(type: "timestamp with time zone", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tblStory", x => x.Id);
                    table.ForeignKey(
                        name: "FK_tblStory_tblEditor_EditorId",
                        column: x => x.EditorId,
                        principalTable: "tblEditor",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "tblStoryLabel",
                columns: table => new
                {
                    StorysId = table.Column<long>(type: "bigint", nullable: false),
                    LabelsId = table.Column<long>(type: "bigint", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tblStoryLabel", x => new { x.StorysId, x.LabelsId });
                    table.ForeignKey(
                        name: "FK_tblStoryLabel_tblStory_StorysId",
                        column: x => x.StorysId,
                        principalTable: "tblStory",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_tblStoryLabel_tblLabel_LabelsId",
                        column: x => x.LabelsId,
                        principalTable: "tblLabel",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "tblNote",
                columns: table => new
                {
                    Id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    StoryId = table.Column<long>(type: "bigint", nullable: false),
                    Content = table.Column<string>(type: "character varying(2048)", maxLength: 2048, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tblNote", x => x.Id);
                    table.ForeignKey(
                        name: "FK_tblNote_tblStory_StoryId",
                        column: x => x.StoryId,
                        principalTable: "tblStory",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_tblEditor_Login",
                table: "tblEditor",
                column: "Login",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_tblStory_EditorId",
                table: "tblStory",
                column: "EditorId");

            migrationBuilder.CreateIndex(
                name: "IX_tblStory_Title",
                table: "tblStory",
                column: "Title",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_tblStoryLabel_LabelsId",
                table: "tblStoryLabel",
                column: "LabelsId");

            migrationBuilder.CreateIndex(
                name: "IX_tblLabel_Name",
                table: "tblLabel",
                column: "Name",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_tblNote_StoryId",
                table: "tblNote",
                column: "StoryId");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "tblStoryLabel");

            migrationBuilder.DropTable(
                name: "tblNote");

            migrationBuilder.DropTable(
                name: "tblLabel");

            migrationBuilder.DropTable(
                name: "tblStory");

            migrationBuilder.DropTable(
                name: "tblEditor");
        }
    }
}
