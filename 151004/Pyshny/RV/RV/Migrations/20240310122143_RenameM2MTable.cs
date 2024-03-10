using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace RV.Migrations
{
    /// <inheritdoc />
    public partial class RenameM2MTable : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_NewsSticker_tbl_News_newsId",
                table: "NewsSticker");

            migrationBuilder.DropForeignKey(
                name: "FK_NewsSticker_tbl_Sticker_stickerId",
                table: "NewsSticker");

            migrationBuilder.DropPrimaryKey(
                name: "PK_NewsSticker",
                table: "NewsSticker");

            migrationBuilder.RenameTable(
                name: "NewsSticker",
                newName: "tbl_NewsSticker");

            migrationBuilder.RenameIndex(
                name: "IX_NewsSticker_stickerId",
                table: "tbl_NewsSticker",
                newName: "IX_tbl_NewsSticker_stickerId");

            migrationBuilder.RenameIndex(
                name: "IX_NewsSticker_newsId",
                table: "tbl_NewsSticker",
                newName: "IX_tbl_NewsSticker_newsId");

            migrationBuilder.AddPrimaryKey(
                name: "PK_tbl_NewsSticker",
                table: "tbl_NewsSticker",
                column: "id");

            migrationBuilder.AddForeignKey(
                name: "FK_tbl_NewsSticker_tbl_News_newsId",
                table: "tbl_NewsSticker",
                column: "newsId",
                principalTable: "tbl_News",
                principalColumn: "id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_tbl_NewsSticker_tbl_Sticker_stickerId",
                table: "tbl_NewsSticker",
                column: "stickerId",
                principalTable: "tbl_Sticker",
                principalColumn: "id",
                onDelete: ReferentialAction.Cascade);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_tbl_NewsSticker_tbl_News_newsId",
                table: "tbl_NewsSticker");

            migrationBuilder.DropForeignKey(
                name: "FK_tbl_NewsSticker_tbl_Sticker_stickerId",
                table: "tbl_NewsSticker");

            migrationBuilder.DropPrimaryKey(
                name: "PK_tbl_NewsSticker",
                table: "tbl_NewsSticker");

            migrationBuilder.RenameTable(
                name: "tbl_NewsSticker",
                newName: "NewsSticker");

            migrationBuilder.RenameIndex(
                name: "IX_tbl_NewsSticker_stickerId",
                table: "NewsSticker",
                newName: "IX_NewsSticker_stickerId");

            migrationBuilder.RenameIndex(
                name: "IX_tbl_NewsSticker_newsId",
                table: "NewsSticker",
                newName: "IX_NewsSticker_newsId");

            migrationBuilder.AddPrimaryKey(
                name: "PK_NewsSticker",
                table: "NewsSticker",
                column: "id");

            migrationBuilder.AddForeignKey(
                name: "FK_NewsSticker_tbl_News_newsId",
                table: "NewsSticker",
                column: "newsId",
                principalTable: "tbl_News",
                principalColumn: "id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_NewsSticker_tbl_Sticker_stickerId",
                table: "NewsSticker",
                column: "stickerId",
                principalTable: "tbl_Sticker",
                principalColumn: "id",
                onDelete: ReferentialAction.Cascade);
        }
    }
}
