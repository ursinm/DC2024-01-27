using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace RV.Migrations
{
    /// <inheritdoc />
    public partial class addUniqueFields : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_tbl_Sticker_name",
                table: "tbl_Sticker",
                column: "name",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_tbl_News_title",
                table: "tbl_News",
                column: "title",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_tbl_User_login",
                table: "tbl_User",
                column: "login",
                unique: true);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_tbl_Sticker_name",
                table: "tbl_Sticker");

            migrationBuilder.DropIndex(
                name: "IX_tbl_News_title",
                table: "tbl_News");

            migrationBuilder.DropIndex(
                name: "IX_tbl_User_login",
                table: "tbl_User");
        }
    }
}
