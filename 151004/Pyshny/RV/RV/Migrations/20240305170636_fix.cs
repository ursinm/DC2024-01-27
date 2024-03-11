using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace RV.Migrations
{
    /// <inheritdoc />
    public partial class fix : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_NewsSticker_tbl_News_NewsId",
                table: "NewsSticker");

            migrationBuilder.DropForeignKey(
                name: "FK_NewsSticker_tbl_Sticker_StickerId",
                table: "NewsSticker");

            migrationBuilder.DropForeignKey(
                name: "FK_tbl_News_tbl_User_UserId",
                table: "tbl_News");

            migrationBuilder.DropForeignKey(
                name: "FK_tbl_Note_tbl_News_NewsId",
                table: "tbl_Note");

            migrationBuilder.RenameColumn(
                name: "NewsId",
                table: "tbl_Note",
                newName: "newsId");

            migrationBuilder.RenameIndex(
                name: "IX_tbl_Note_NewsId",
                table: "tbl_Note",
                newName: "IX_tbl_Note_newsId");

            migrationBuilder.RenameColumn(
                name: "UserId",
                table: "tbl_News",
                newName: "userId");

            migrationBuilder.RenameIndex(
                name: "IX_tbl_News_UserId",
                table: "tbl_News",
                newName: "IX_tbl_News_userId");

            migrationBuilder.RenameColumn(
                name: "StickerId",
                table: "NewsSticker",
                newName: "stickerId");

            migrationBuilder.RenameColumn(
                name: "NewsId",
                table: "NewsSticker",
                newName: "newsId");

            migrationBuilder.RenameIndex(
                name: "IX_NewsSticker_StickerId",
                table: "NewsSticker",
                newName: "IX_NewsSticker_stickerId");

            migrationBuilder.RenameIndex(
                name: "IX_NewsSticker_NewsId",
                table: "NewsSticker",
                newName: "IX_NewsSticker_newsId");

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

            migrationBuilder.AddForeignKey(
                name: "FK_tbl_News_tbl_User_userId",
                table: "tbl_News",
                column: "userId",
                principalTable: "tbl_User",
                principalColumn: "id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_tbl_Note_tbl_News_newsId",
                table: "tbl_Note",
                column: "newsId",
                principalTable: "tbl_News",
                principalColumn: "id",
                onDelete: ReferentialAction.Cascade);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_NewsSticker_tbl_News_newsId",
                table: "NewsSticker");

            migrationBuilder.DropForeignKey(
                name: "FK_NewsSticker_tbl_Sticker_stickerId",
                table: "NewsSticker");

            migrationBuilder.DropForeignKey(
                name: "FK_tbl_News_tbl_User_userId",
                table: "tbl_News");

            migrationBuilder.DropForeignKey(
                name: "FK_tbl_Note_tbl_News_newsId",
                table: "tbl_Note");

            migrationBuilder.RenameColumn(
                name: "newsId",
                table: "tbl_Note",
                newName: "NewsId");

            migrationBuilder.RenameIndex(
                name: "IX_tbl_Note_newsId",
                table: "tbl_Note",
                newName: "IX_tbl_Note_NewsId");

            migrationBuilder.RenameColumn(
                name: "userId",
                table: "tbl_News",
                newName: "UserId");

            migrationBuilder.RenameIndex(
                name: "IX_tbl_News_userId",
                table: "tbl_News",
                newName: "IX_tbl_News_UserId");

            migrationBuilder.RenameColumn(
                name: "stickerId",
                table: "NewsSticker",
                newName: "StickerId");

            migrationBuilder.RenameColumn(
                name: "newsId",
                table: "NewsSticker",
                newName: "NewsId");

            migrationBuilder.RenameIndex(
                name: "IX_NewsSticker_stickerId",
                table: "NewsSticker",
                newName: "IX_NewsSticker_StickerId");

            migrationBuilder.RenameIndex(
                name: "IX_NewsSticker_newsId",
                table: "NewsSticker",
                newName: "IX_NewsSticker_NewsId");

            migrationBuilder.AddForeignKey(
                name: "FK_NewsSticker_tbl_News_NewsId",
                table: "NewsSticker",
                column: "NewsId",
                principalTable: "tbl_News",
                principalColumn: "id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_NewsSticker_tbl_Sticker_StickerId",
                table: "NewsSticker",
                column: "StickerId",
                principalTable: "tbl_Sticker",
                principalColumn: "id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_tbl_News_tbl_User_UserId",
                table: "tbl_News",
                column: "UserId",
                principalTable: "tbl_User",
                principalColumn: "id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_tbl_Note_tbl_News_NewsId",
                table: "tbl_Note",
                column: "NewsId",
                principalTable: "tbl_News",
                principalColumn: "id",
                onDelete: ReferentialAction.Cascade);
        }
    }
}
