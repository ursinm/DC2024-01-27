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
                name: "tblCreator",
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
                    table.PrimaryKey("PK_tblCreator", x => x.Id);
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
                name: "tblIssue",
                columns: table => new
                {
                    Id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    CreatorId = table.Column<long>(type: "bigint", nullable: false),
                    Title = table.Column<string>(type: "character varying(64)", maxLength: 64, nullable: false),
                    Content = table.Column<string>(type: "character varying(2048)", maxLength: 2048, nullable: false),
                    Created = table.Column<DateTime>(type: "timestamp with time zone", nullable: false),
                    Modified = table.Column<DateTime>(type: "timestamp with time zone", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tblIssue", x => x.Id);
                    table.ForeignKey(
                        name: "FK_tblIssue_tblCreator_CreatorId",
                        column: x => x.CreatorId,
                        principalTable: "tblCreator",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "tblIssueLabel",
                columns: table => new
                {
                    IssuesId = table.Column<long>(type: "bigint", nullable: false),
                    LabelsId = table.Column<long>(type: "bigint", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tblIssueLabel", x => new { x.IssuesId, x.LabelsId });
                    table.ForeignKey(
                        name: "FK_tblIssueLabel_tblIssue_IssuesId",
                        column: x => x.IssuesId,
                        principalTable: "tblIssue",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_tblIssueLabel_tblLabel_LabelsId",
                        column: x => x.LabelsId,
                        principalTable: "tblLabel",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "tblPost",
                columns: table => new
                {
                    Id = table.Column<long>(type: "bigint", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    IssueId = table.Column<long>(type: "bigint", nullable: false),
                    Content = table.Column<string>(type: "character varying(2048)", maxLength: 2048, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tblPost", x => x.Id);
                    table.ForeignKey(
                        name: "FK_tblPost_tblIssue_IssueId",
                        column: x => x.IssueId,
                        principalTable: "tblIssue",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_tblCreator_Login",
                table: "tblCreator",
                column: "Login",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_tblIssue_CreatorId",
                table: "tblIssue",
                column: "CreatorId");

            migrationBuilder.CreateIndex(
                name: "IX_tblIssue_Title",
                table: "tblIssue",
                column: "Title",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_tblIssueLabel_LabelsId",
                table: "tblIssueLabel",
                column: "LabelsId");

            migrationBuilder.CreateIndex(
                name: "IX_tblLabel_Name",
                table: "tblLabel",
                column: "Name",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_tblPost_IssueId",
                table: "tblPost",
                column: "IssueId");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "tblIssueLabel");

            migrationBuilder.DropTable(
                name: "tblPost");

            migrationBuilder.DropTable(
                name: "tblLabel");

            migrationBuilder.DropTable(
                name: "tblIssue");

            migrationBuilder.DropTable(
                name: "tblCreator");
        }
    }
}
